
using System.Text.RegularExpressions;

public class JVMMethod : JVMMember
{
    private CodeAttribute codeAttribute;
    private IJVMConstPoolType[] constPool;
    private string descriptor;
    private JVMInterface owner;
    private int argumentSize;

    public JVMMethod(MethodInfo methodInfo, IJVMConstPoolType[] constPool, JVMInterface owner)
        : base(methodInfo.getName(), VisibilityHelper.GetVisibility(methodInfo.getAccessFlags()))
    {
        this.codeAttribute = methodInfo.getCodeAttribute();
        this.descriptor = methodInfo.getDescriptor();
        this.constPool = constPool;
        this.owner = owner;

        argumentSize = calculateArgumentSize();
    }

    public CallFrame invokeMethod(CallFrame callerFrame, IUnitSerializable[] args)
    {
        CallFrame newFrame = new CallFrame(callerFrame, constPool, this);

        for (int i = 0; i < args.Length; i++)
            newFrame.setLocal(i, args[i]);

        callerFrame.setChildFrame(newFrame);
        return newFrame;
    }

    public string getDescriptor()
    {
        return descriptor;
    }

    public static JVMMethod getMethodFromCpIndex(CallFrame frame, int index)
    {
        CpMethodRef cpMethod = (CpMethodRef)frame.getConst(index);
        int nameAndTypeIndex = cpMethod.getNameAndTypeIndex();
        int classIndex = cpMethod.getClassIndex();

        CpClass cpClass = (CpClass)frame.getConst(classIndex);
        CpNameAndIndex nameAndType = (CpNameAndIndex)frame.getConst(nameAndTypeIndex);
        CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
        CpUtf8 fieldName = (CpUtf8)frame.getConst(nameAndType.getNameIndex());
        CpUtf8 descriptor = (CpUtf8)frame.getConst(nameAndType.getDescriptorIndex());

        JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
        JVMMethod method = theClass.getMethod(fieldName.getString(), descriptor.getString());
        return method;
    }

    private int calculateArgumentSize()
    {
        string descriptor = this.getDescriptor();
        string preParsed = descriptor, preParsed2 = descriptor;

        do
        {
            preParsed2 = preParsed;
            Regex r = new Regex("L[^;]*;");
            preParsed = r.Replace(preParsed, "L", 1);
        } while (!preParsed.Equals(preParsed2));

        preParsed = preParsed.Substring(preParsed.IndexOf('(') + 1);
        preParsed = preParsed.Substring(0, preParsed.LastIndexOf(')'));

        preParsed = preParsed.Replace("\\[.", "L");
        preParsed = preParsed.Replace("\\[", "");

        int size = 0;
        for (int i = 0; i < preParsed.Length; i++)
        {
            if (preParsed[i] == 'D' || preParsed[i] == 'J')
                size += 2;
            else
                size++;
        }

        return size;
    }

    public int getArgumentsSize()
    {
        return argumentSize;
    }

    public byte[] getCode()
    {
        return this.codeAttribute.getCode();
    }

    public int getMaxStack()
    {
        return this.codeAttribute.getMaxStack();
    }

    public int getMaxLocals()
    {
        return this.codeAttribute.getMaxLocals();
    }

    public override string ToString()
    {
        return this.getDescriptor() + " " + this.getMemberName();
    }

    public JVMInterface getOwner()
    {
        return owner;
    }
}
