

public class JVMLong : JVMNumber, IJVMConstPoolType
{

    private long data;

    public JVMLong()
    {
        data = 0;
    }

    public JVMLong(long data)
    {
        this.data = data;
    }

    public override Unit[] serialize()
    {
        Unit[] result = new Unit[2];
        JVMInteger view = new JVMInteger((int)(data >> 32));
        result[0] = view.serialize()[0];
        view.setData((int)(data & 0xFFFFFFFF));
        result[1] = view.serialize()[0];
        return result;
    }

    public override void deserialize(Unit[] data)
    {
        JVMInteger h1 = new JVMInteger(0);
        JVMInteger h2 = new JVMInteger(0);
        h1.deserialize(new Unit[] { data[0] });
        h2.deserialize(new Unit[] { data[1] });
        this.data = (((long)h1.intValue()) << 32) | (long)h2.intValue();
    }

    public override int getSerializedSize()
    {
        return 2;
    }

    public void setData(long doubleToLongBits)
    {
        this.data = doubleToLongBits;
    }

    public IUnitSerializable toIUnitSerializable()
    {
        return this;
    }

    public override int intValue()
    {
        return (int)this.data;
    }

    public override long longValue()
    {
        return this.data;
    }

    public override float floatValue()
    {
        return this.data;
    }

    public override double doubleValue()
    {
        return this.data;
    }

    public override void set(JVMNumber other)
    {
        this.data = other.longValue();
    }

    public override string ToString()
    {
        return "" + this.longValue();
    }
}
