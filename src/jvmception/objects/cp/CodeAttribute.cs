using System.IO;

public class CodeAttribute : FieldAttribute
{

    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private byte[] code;
    private FieldAttribute[] attributes;

    public CodeAttribute(int attributeLenght, CpInfo[] cpInfo, BinaryReaderBigEndian dis)
        : base("Code", attributeLenght, dis)
    {

        maxStack = dis.ReadUInt16();
        maxLocals = dis.ReadUInt16();
        codeLength = dis.ReadInt32();
        code = dis.ReadBytes(codeLength);

        /* TODO: Exceptions */
        int exceptionTableLength = dis.ReadUInt16();
        dis.ReadBytes(8 * exceptionTableLength);

        int attributesCount = dis.ReadUInt16();
        attributes = FieldAttribute.loadAttributes(dis, cpInfo, attributesCount);
    }

    internal CodeAttribute() : base("Code")
    {

        this.maxLocals = 1;
        code = new byte[] { (byte)(0xB1) };
    }

    public FieldAttribute[] getAttributes()
    {
        return attributes;
    }

    public int getCodeLength()
    {
        return codeLength;
    }

    public byte[] getCode()
    {
        return code;
    }

    public int getMaxLocals()
    {
        return maxLocals;
    }

    public int getMaxStack()
    {
        return maxStack;
    }
}
