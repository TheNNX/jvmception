using System.IO;

public class CpFloat : CpInfo
{
    private float floatingPoint;

    public CpFloat(BinaryReaderBigEndian dis)
    {
        floatingPoint = dis.ReadSingle();
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return new JVMFloat(floatingPoint);
    }
}
