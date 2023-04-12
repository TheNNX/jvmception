
public abstract class JVMNumber : Number, IUnitSerializable
{
    public abstract void set(JVMNumber other);

    public override abstract string ToString();

    public abstract Unit[] serialize();
    public abstract void deserialize(Unit[] data);
    public abstract int getSerializedSize();
}
