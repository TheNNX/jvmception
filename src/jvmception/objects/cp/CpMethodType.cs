

using System.IO;

public class CpMethodType : CpInfo
{
    private int descriptorIndex;

    public CpMethodType(BinaryReaderBigEndian dis)
    {
        descriptorIndex = dis.ReadUInt16();
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return JVMReference.NULL_REFERENCE;
    }
}
