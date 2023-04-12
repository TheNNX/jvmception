

using System;

public class JVMDouble : JVMFloatingNumber, IJVMConstPoolType
{
    private double data;
    private JVMLong longConverter = new JVMLong();

    public JVMDouble()
    {
        this.data = 0.0;
    }

    public JVMDouble(double data)
    {
        this.data = data;
    }

    public double getData()
    {
        return data;
    }

    public void setData(double data)
    {
        this.data = data;
    }

    public override Unit[] serialize()
    {
        byte[] bytes = BitConverter.GetBytes(this.data);
        longConverter.setData(BitConverter.ToInt64(bytes, 0));
        return longConverter.serialize();
    }

    public override void deserialize(Unit[] data)
    {
        longConverter.deserialize(data);
        byte[] bytes = BitConverter.GetBytes(longConverter.intValue());
        this.data = BitConverter.ToDouble(bytes, 0);
    }

    public override int getSerializedSize()
    {
        return 2;
    }

    public IUnitSerializable toIUnitSerializable()
    {
        return this;
    }

    public override int intValue()
    {
        return (int)getData();
    }

    public override long longValue()
    {
        return (long)getData();
    }

    public override float floatValue()
    {
        return (float)getData();
    }

    public override double doubleValue()
    {
        return getData();
    }

    public override void set(JVMNumber other)
    {
        this.data = other.doubleValue();
    }

    public override string ToString()
    {
        return "" + this.doubleValue();
    }
}
