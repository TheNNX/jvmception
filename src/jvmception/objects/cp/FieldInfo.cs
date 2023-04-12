using System.IO;
public class FieldInfo
{
    private int accessFlags;
    private string name;
    private string descriptor;
    private int attributesCount;
    private FieldAttribute[] attributes;

    public FieldInfo(BinaryReaderBigEndian dis, CpInfo[] cpInfo)
    {
        accessFlags = dis.ReadUInt16();
        int nameIndex = dis.ReadUInt16();
        int descriptorIndex = dis.ReadUInt16();
        attributesCount = dis.ReadUInt16();

        name = ((CpUtf8)cpInfo[nameIndex]).ToString();
        descriptor = ((CpUtf8)cpInfo[descriptorIndex]).ToString();

        attributes = FieldAttribute.loadAttributes(dis, cpInfo, attributesCount);
    }

    protected FieldInfo(string name, string descriptor)
    {
        this.name = name;
        this.descriptor = descriptor;
    }

    public string getName()
    {
        return this.name;
    }

    public string getDescriptor()
    {
        return descriptor;
    }

    public int getAccessFlags()
    {
        return accessFlags;
    }

    public FieldAttribute[] getAttributes()
    {
        return this.attributes;
    }
}