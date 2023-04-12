using System.IO;

public class CpInteger : CpInfo
{
    private int integer;

    public CpInteger(BinaryReaderBigEndian dis)
    {
        integer = dis.ReadInt32();
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return new JVMInteger(integer);
    }
}
