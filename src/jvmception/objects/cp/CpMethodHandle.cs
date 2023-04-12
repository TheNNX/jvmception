

using System.IO;

public class CpMethodHandle : CpInfo
{
    private int referenceKind;
    private int referenceIndex;

    public CpMethodHandle(BinaryReaderBigEndian dis)
    {
        referenceKind = dis.ReadByte();
        referenceIndex = dis.ReadUInt16();
    }

    public int getRefKind()
    {
        return referenceKind;
    }

    public int getRefIndex()
    {
        return referenceIndex;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return JVMReference.NULL_REFERENCE;
    }
}
