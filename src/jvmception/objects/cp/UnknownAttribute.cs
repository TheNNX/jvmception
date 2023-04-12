
using System.IO;

public class UnknownAttribute : FieldAttribute
{

    public UnknownAttribute(string attributeName, int attributeLenght, BinaryReaderBigEndian dis)
        : base(attributeName, attributeLenght, dis)
    {
        dis.ReadBytes(attributeLenght);
    }
}