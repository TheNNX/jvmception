using System.IO;

public abstract class FieldAttribute
{
    private string attributeName;

    protected FieldAttribute(string attributeName, int attributeLenght, BinaryReaderBigEndian dis)
    {
        this.attributeName = attributeName;
    }

    public string getAttributeName()
    {
        return this.attributeName;
    }

    protected FieldAttribute(string attributeName)
    {
        this.attributeName = attributeName;
    }

    private static FieldAttribute loadAttribute(BinaryReaderBigEndian dis, CpInfo[] cpInfo)
    {
        int attributeNameIndex = dis.ReadUInt16();
        int attributeLength = dis.ReadInt32();
        string attributeName = ((CpUtf8)cpInfo[attributeNameIndex]).ToString();

        switch (attributeName)
        {
        default:
            return new UnknownAttribute(attributeName, attributeLength, dis);
        case "Code":
            return new CodeAttribute(attributeLength, cpInfo, dis);
        }
    }

    public static FieldAttribute[] loadAttributes(BinaryReaderBigEndian dis, CpInfo[] cpInfo, int attributesCount)
    {
        FieldAttribute[] attributes = new FieldAttribute[attributesCount];

        for (int i = 0; i < attributesCount; i++)
        {
            attributes[i] = FieldAttribute.loadAttribute(dis, cpInfo);
        }

        return attributes;
    }
}