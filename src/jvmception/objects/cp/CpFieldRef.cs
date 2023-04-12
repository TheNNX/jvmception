
using System.IO;

public class CpFieldRef : CpInfo
{
    private int classIndex;
    private int nameAndTypeIndex;

    public CpFieldRef(BinaryReaderBigEndian stream)
    {
        classIndex = stream.ReadUInt16();
        nameAndTypeIndex = stream.ReadUInt16();
    }

    public int getClassIndex()
    {
        return classIndex;
    }

    public int getNameAndTypeIndex()
    {
        return nameAndTypeIndex;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return JVMReference.NULL_REFERENCE;
    }
}
