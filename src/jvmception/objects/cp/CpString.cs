

using System.IO;

public class CpString : CpInfo
{
    private int stringIndex;
    private JVMReference reference = null;
    private CpInfo[] cpInfo;

    public CpString(BinaryReaderBigEndian dis, CpInfo[] cpInfo)
    {
        stringIndex = dis.ReadUInt16();
        this.cpInfo = cpInfo;
    }

    public int getStringIndex()
    {
        return stringIndex;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        if (reference == null)
        {
            cpInfo = null;
            reference = (JVMReference)((CpUtf8)cpInfo[stringIndex]).toIUnitSerializable();
        }
        return reference;
    }
}
