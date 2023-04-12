

using System;

public class JVMFloat : JVMFloatingNumber, IJVMConstPoolType
{

    private float data;
    private JVMInteger integerConverter = new JVMInteger(0);

    public JVMFloat()
    {
        this.data = 0;
    }

    public JVMFloat(float data)
    {
        this.data = data;
    }

    public override Unit[] serialize()
    {
        byte[] bytes = BitConverter.GetBytes(this.data);
        integerConverter.setData(BitConverter.ToInt32(bytes, 0));
        return integerConverter.serialize();
    }

    public override void deserialize(Unit[] data)
    {
        integerConverter.deserialize(data);
        byte[] bytes = BitConverter.GetBytes(integerConverter.intValue());
        this.data = BitConverter.ToSingle(bytes, 0);
    }

    public override int getSerializedSize()
    {
        return 1;
    }

    public IUnitSerializable toIUnitSerializable()
    {
        return this;
    }

    public void setData(float data)
    {
        this.data = data;
    }

    public override int intValue()
    {
        return (int)data;
    }

    public override long longValue()
    {
        return (long)data;
    }

    public override float floatValue()
    {
        return data;
    }

    public override double doubleValue()
    {
        return data;
    }

    public override void set(JVMNumber other)
    {
        this.data = other.floatValue();
    }

    public override string ToString()
    {
        return "" + this.floatValue();
    }
}
