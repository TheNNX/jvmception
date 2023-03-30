package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;

import jvmception.CallFrame;
import jvmception.InstructionDecoder;
import jvmception.jvmtypes.IJVMConstPoolType;
import jvmception.objects.JVMClass;
import jvmception.objects.JVMField;
import jvmception.objects.JVMInterface;
import jvmception.objects.JVMMethod;
import jvmception.objects.Visibility;

public class JVMClassFileLoader {
	private static Hashtable<String, JVMClass> loadedClasses;
	private static Hashtable<String, JVMInterface> loadedInterfaces;
	
	static {
		loadedClasses = new Hashtable<>();
		
		String objName = "java/lang/Object";
		loadedClasses.put(objName, new JVMClass(objName, null, new JVMInterface[0], Visibility.PUBLIC, null));
		
		loadedInterfaces = new Hashtable<>();
	}
	
	public static JVMInterface loadClass(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(fis);
		
		int magic = dis.readInt();
		/* TODO: verify magic number */
		
		int version = dis.readInt();
		/* TODO: version */
		
		int constantPoolCount = dis.readUnsignedShort() - 1;
		CpInfo[] cpInfo = new CpInfo[constantPoolCount + 1];
		
		for (int i = 0; i < constantPoolCount; ) {
			cpInfo[i + 1] = CpInfo.readCpInfo(dis, cpInfo);
			i += cpInfo[i + 1].getCpIncrement();
		}
		
		int accessFlags = dis.readUnsignedShort();
		int classIdx = dis.readUnsignedShort();
		int baseClassIdx = dis.readUnsignedShort();
		
		String className = ((CpUtf8)cpInfo[((CpClass)cpInfo[classIdx]).getNameIndex()]).toString();
		String baseClassName = ((CpUtf8)cpInfo[((CpClass)cpInfo[baseClassIdx]).getNameIndex()]).toString();
		
		if (loadedClasses.containsKey(className)) {
			throw new ClassCircularityError();
		}
		
		if (loadedClasses.containsKey(baseClassName) == false) {
			String[] paths = baseClassName.split("/");
			String baseFilename = paths[paths.length - 1];
			loadClass(baseFilename);
		}
		
		int numberOfInterfaces = dis.readUnsignedShort();
		JVMInterface[] interfaces = new JVMInterface[numberOfInterfaces];
		
		for (int i = 0; i < numberOfInterfaces; i++) {
			int idx = dis.readUnsignedShort();
			String interfaceName = ((CpUtf8)cpInfo[((CpClass)cpInfo[idx]).getNameIndex()]).toString();
			
			if (loadedClasses.contains(interfaceName) == false) {
				String[] paths = interfaceName.split("/");
				String interfaceFilename = paths[paths.length - 1];
				loadClass(Path.of(fileName).getParent().toAbsolutePath().toString() +"/"+ interfaceFilename + ".class");
			}
			
			interfaces[i] = loadedInterfaces.get(interfaceName);
		}
		
		JVMInterface result;
		if ((accessFlags & 0x0200) == 0) {
			JVMClass baseClass = loadedClasses.get(baseClassName);
			JVMClass loadedClass = new JVMClass(className, baseClass, interfaces, Visibility.PUBLIC, cpInfo);
			loadedClasses.put(className, loadedClass);
			result = loadedClass;
			
			int numberOfFields = dis.readUnsignedShort();
			JVMField[] fields = new JVMField[numberOfFields];
			for (int i = 0; i < numberOfFields; i++) {
				FieldInfo info = new FieldInfo(dis, cpInfo);
				fields[i] = new JVMField(info);
			}
			loadedClass.setFields(fields);
		}		
		else {
			JVMInterface loadedInterface = new JVMInterface(className, interfaces, Visibility.PUBLIC);
			loadedInterfaces.put(className, loadedInterface);
			result = loadedInterface;
			
			int zero = dis.readUnsignedShort();
			if (zero != 0)
				throw new ClassFormatError("Interface with fields.");
		}
		
		int numberOfMethods = dis.readUnsignedShort();
		JVMMethod[] methods = new JVMMethod[numberOfMethods];
		for (int i = 0; i < numberOfMethods; i++) {
			MethodInfo methodInfo = new MethodInfo(dis, cpInfo);
			methods[i] = new JVMMethod(methodInfo, cpInfo);
		}
				
		int numberOfAttributes = dis.readUnsignedShort();
		FieldAttribute[] attributes = FieldAttribute.loadAttributes(dis, cpInfo, numberOfAttributes);
		
		result.setMethods(methods);
		
		dis.close();
		fis.close();
		return result;
	}  
	
	public static JVMClass getClassByName(String className) throws ClassNotFoundException {
		if (loadedClasses.containsKey(className) == false)
			throw new ClassNotFoundException(className);
		
		return loadedClasses.get(className);
	}
	
	public static JVMInterface getInterfaceByName(String interfaceName) throws ClassNotFoundException {
		if (loadedInterfaces.containsKey(interfaceName) == false)
			throw new ClassNotFoundException(interfaceName);
		
		return loadedInterfaces.get(interfaceName);
	}
	
	public static void main(String[] args) {
		JVMClassFileLoader cfl = new JVMClassFileLoader();
		JVMClass mainClass;
		
		try {
			mainClass = (JVMClass) cfl.loadClass("C:\\Users\\Marcin\\eclipse-workspace\\java\\bin\\jfejkf\\Shit.class");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			CallFrame f = new CallFrame(100, 100, null, mainClass.getConstPool(), mainClass.getMethod("main", "([Ljava/lang/String;)V").getCode());
			InstructionDecoder decoder = new InstructionDecoder();
			
			while (f != null) {
				if (f.getChildFrame() != null) {
					f = f.getChildFrame();
				}
				if (f.getIsReturnPending()) {
					f = f.getParent();
					if (f == null)
						continue;
					f.setChildFrame(null);
				}
				
				decoder.execute(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
