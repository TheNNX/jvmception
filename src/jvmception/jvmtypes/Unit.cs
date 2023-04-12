
public class Unit : IUnitSerializable
{
    private byte[] data;

    public Unit(byte[] data)
    {
        this.data = data;
    }

    public static IUnitSerializable getTypeFromCode(char c)
    {
        switch (c)
        {
        case 'B':
        case 'C':
            /* TODO: chars and bytes shlouldn't be ints - handle sign extension etc.
             */
            return new JVMInteger();
        case 'D':
            return new JVMDouble();
        case 'F':
            return new JVMFloat();
        case 'I':
            return new JVMInteger();
        case 'J':
            return new JVMLong();
        case 'L':
            return new JVMReference();
        case 'S':
            /* TODO: short */
            return new JVMInteger();
        case 'Z':
            /* TODO: bool */
            return new JVMInteger();
        case '[':
            return new JVMArrayReference();
        default:
            return null;
        }
    }

    public Unit(int data)
    {
        this.data = new byte[4];
        this.setInt(data);
    }

    public Unit()
    {
    }

    public byte getByte(int idx)
    {
        return this.data[idx];
    }

    public void setByte(int idx, byte data)
    {
        this.data[idx] = data;
    }

    public int getInt()
    {
        return ((data[0] & 0xFF) << 24) | ((data[1] & 0xFF) << 16) | ((data[2] & 0xFF) << 8) | (data[3] & 0xFF);
    }

    public void setInt(int data)
    {
        this.data[0] = (byte)((data & 0xFF000000) >> 24);
        this.data[1] = (byte)((data & 0xFF0000) >> 16);
        this.data[2] = (byte)((data & 0xFF00) >> 8);
        this.data[3] = (byte)((data & 0xFF));
    }

    public Unit[] serialize()
    {
        return new Unit[] { new Unit(this.data) };
    }

    public void deserialize(Unit[] data)
    {
        this.data = (byte[])data[0].data.Clone();
    }

    public int getSerializedSize()
    {
        return 1;
    }

    public override string ToString()
    {
        string result = "{";
        for (int i = 0; i < 4; i++)
            result += ((int)this.data[i]) + ((i == 3) ? "}" : ", ");

        return result;
    }
}
