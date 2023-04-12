using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;

public abstract class JVMClassFileLoader
{
    private static Dictionary<string, JVMClass> loadedClasses;
    private static Dictionary<string, JVMInterface> loadedInterfaces;

    static JVMClassFileLoader()
    {
        loadedClasses = new Dictionary<string, JVMClass>();

        string objName = "java/lang/Object";
        JVMClass objClass = new JVMClass(objName, null, new JVMInterface[0], Visibility.PUBLIC, null);

        JVMMethod objInit = new JVMMethod(new MethodInfo("<init>", "()V"), null, objClass);
        objClass.setMethods(new JVMMethod[] { objInit });
        objClass.setFields(new JVMField[] {});

        loadedClasses.Add(objName, objClass);
        loadedInterfaces = new Dictionary<string, JVMInterface>();
    }

    public static JVMInterface loadClass(string fileName)
    {
        FileStream fis = new FileStream(fileName, FileMode.Open, FileAccess.Read);
        BinaryReaderBigEndian dis = new BinaryReaderBigEndian(fis);

        int magic = dis.ReadInt32();
        /* TODO: verify magic number */

        int version = dis.ReadInt32();
        /* TODO: version */

        int constantPoolCount = dis.ReadUInt16() - 1;
        CpInfo[] cpInfo = new CpInfo[constantPoolCount + 1];

        for (int i = 0; i < constantPoolCount;)
        {
            cpInfo[i + 1] = CpInfo.readCpInfo(dis, cpInfo);
            i += cpInfo[i + 1].getCpIncrement();
        }

        int accessFlags = dis.ReadUInt16();
        int classIdx = dis.ReadUInt16();
        int baseClassIdx = dis.ReadUInt16();

        string className = ((CpUtf8)cpInfo[((CpClass)cpInfo[classIdx]).getNameIndex()]).ToString();
        string baseClassName = ((CpUtf8)cpInfo[((CpClass)cpInfo[baseClassIdx]).getNameIndex()]).ToString();

        if (loadedClasses.ContainsKey(className))
        {
            throw new ArgumentException();
        }

        if (loadedClasses.ContainsKey(baseClassName) == false)
        {
            string[] paths = baseClassName.Split('/');
            string baseFilename = paths[paths.Length - 1];
            loadClass(baseFilename);
        }

        int numberOfInterfaces = dis.ReadUInt16();
        JVMInterface[] interfaces = new JVMInterface[numberOfInterfaces];

        for (int i = 0; i < numberOfInterfaces; i++)
        {
            int idx = dis.ReadUInt16();
            string interfaceName = ((CpUtf8)cpInfo[((CpClass)cpInfo[idx]).getNameIndex()]).ToString();

            if (loadedClasses.ContainsKey(interfaceName) == false)
            {
                string[] paths = interfaceName.Split('/');
                string interfaceFilename = paths[paths.Length - 1];
                loadClass(System.IO.Path.GetFullPath(fileName) + "/../" + interfaceFilename + ".class");
            }

            interfaces[i] = loadedInterfaces[interfaceName];
        }

        JVMInterface result;
        if ((accessFlags & 0x0200) == 0)
        {
            JVMClass baseClass = loadedClasses[baseClassName];
            JVMClass loadedClass = new JVMClass(className, baseClass, interfaces, Visibility.PUBLIC, cpInfo);
            loadedClasses.Add(className, loadedClass);
            result = loadedClass;

            int numberOfFields = dis.ReadUInt16();
            JVMField[] fields = new JVMField[numberOfFields];
            for (int i = 0; i < numberOfFields; i++)
            {
                FieldInfo info = new FieldInfo(dis, cpInfo);
                fields[i] = new JVMField(info, result);
            }
            loadedClass.setFields(fields);
        }
        else
        {
            JVMInterface loadedInterface = new JVMInterface(className, interfaces, Visibility.PUBLIC);
            loadedInterfaces.Add(className, loadedInterface);
            result = loadedInterface;

            int zero = dis.ReadUInt16();
            if (zero != 0)
                throw new Exception("Interface with fields.");
        }

        int numberOfMethods = dis.ReadUInt16();
        JVMMethod[] methods = new JVMMethod[numberOfMethods];
        for (int i = 0; i < numberOfMethods; i++)
        {
            MethodInfo methodInfo = new MethodInfo(dis, cpInfo);
            methods[i] = new JVMMethod(methodInfo, cpInfo, result);
        }

        int numberOfAttributes = dis.ReadUInt16();
        FieldAttribute[] attributes = FieldAttribute.loadAttributes(dis, cpInfo, numberOfAttributes);

        result.setMethods(methods);

        dis.Close();
        fis.Close();
        return result;
    }

    public static JVMClass getClassByName(string className)
    {
        if (loadedClasses.ContainsKey(className) == false)
            throw new Exception(className);

        return loadedClasses[className];
    }

    public static JVMInterface getInterfaceByName(string interfaceName)
    {
        if (loadedInterfaces.ContainsKey(interfaceName) == false)
            throw new Exception(interfaceName);

        return loadedInterfaces[interfaceName];
    }

    public static void Main(string[] args)
    {
        JVMClass mainClass;

        mainClass = (JVMClass)JVMClassFileLoader.loadClass(args[0]);

        Stopwatch s = Stopwatch.StartNew();
        CallFrame f =
            new CallFrame(null, mainClass.getConstPool(), mainClass.getMethod("main", "([Ljava/lang/String;)V"));

        InstructionDecoder decoder = new InstructionDecoder();

        while (f != null)
        {
            if (f.getChildFrame() != null)
            {
                f = f.getChildFrame();
            }
            if (f.getIsReturnPending())
            {
                f = f.getParent();
                if (f == null)
                    continue;
                f.setChildFrame(null);
            }

            decoder.execute(f);
        }
        s.Stop();
        Console.WriteLine("Elapsed " + s.ElapsedMilliseconds);

        Console.ReadKey();
    }
}
