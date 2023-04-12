

using System.IO;

public class CpDouble : CpInfo
{
    private double doubleFloating;

    public CpDouble(BinaryReaderBigEndian dis)
    {
        doubleFloating = dis.ReadDouble();
    }

    public double getDouble()
    {
        return doubleFloating;
    }

    public override int getCpIncrement()
    {
        return 2;
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return new JVMDouble(doubleFloating);
    }
}
