

using System.IO;

public class CpLong : CpInfo
{
    private long longInteger;

    public CpLong(BinaryReaderBigEndian dis)
    {
        longInteger = dis.ReadInt64();
    }

    public long getLong()
    {
        return longInteger;
    }

    public override int getCpIncrement()
    {
        return 2;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return new JVMLong(longInteger);
    }
}
