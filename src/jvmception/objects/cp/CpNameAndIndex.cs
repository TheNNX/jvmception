
using System.IO;

public class CpNameAndIndex : CpInfo
{
    int nameIndex;
    int descriptorIndex;

    public CpNameAndIndex(BinaryReaderBigEndian dis)
    {
        nameIndex = dis.ReadUInt16();
        descriptorIndex = dis.ReadUInt16();
    }

    public int getNameIndex()
    {
        return nameIndex;
    }

    public int getDescriptorIndex()
    {
        return descriptorIndex;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return JVMReference.NULL_REFERENCE;
    }
}
