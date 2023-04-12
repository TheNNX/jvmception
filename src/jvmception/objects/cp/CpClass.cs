
using System.IO;

public class CpClass : CpInfo
{
    private int nameIndex;

    public int getNameIndex()
    {
        return this.nameIndex;
    }

    public CpClass(BinaryReaderBigEndian stream)
    {
        nameIndex = stream.ReadUInt16();
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return JVMReference.NULL_REFERENCE;
    }
}
