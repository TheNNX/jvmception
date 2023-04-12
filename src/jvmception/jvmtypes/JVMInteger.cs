

public class JVMInteger : JVMNumber, IJVMConstPoolType
{

    private Unit data;

    public JVMInteger(int data)
    {
        this.data = new Unit(data);
    }

    public JVMInteger()
    {
        this.data = new Unit(0);
    }

    public void setData(int data)
    {
        this.data.setInt(data);
    }

    public override Unit[] serialize()
    {
        return new Unit[] { data };
    }

    public override void deserialize(Unit[] data)
    {
        this.data = data[0];
    }

    public override int getSerializedSize()
    {
        return 1;
    }

    public IUnitSerializable toIUnitSerializable()
    {
        return this;
    }

    public override int intValue()
    {
        return data.getInt();
    }

    public override long longValue()
    {
        return data.getInt();
    }

    public override float floatValue()
    {
        return data.getInt();
    }

    public override double doubleValue()
    {
        return data.getInt();
    }

    public override void set(JVMNumber other)
    {
        this.data.setInt(other.intValue());
    }

    public override string ToString()
    {
        return "" + this.intValue();
    }
}
