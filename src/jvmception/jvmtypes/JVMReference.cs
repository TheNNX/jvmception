

public class JVMReference : IUnitSerializable, IJVMConstPoolType
{
    private int objectId;
    public static readonly JVMReference NULL_REFERENCE = new JVMReference(0);

    public JVMReference(int objectId)
    {
        this.objectId = objectId;
    }

    public JVMReference()
    {
        this.objectId = 0;
    }

    public JVMReference(JVMInstance instance)
    {
        this.objectId = instance.getObjectId();
    }

    public Unit[] serialize()
    {
        JVMInteger serialize = new JVMInteger(this.objectId);
        return serialize.serialize();
    }

    public void deserialize(Unit[] data)
    {
        JVMInteger deserialize = new JVMInteger();
        deserialize.deserialize(data);
        this.objectId = deserialize.intValue();
    }

    public int getSerializedSize()
    {
        return 1;
    }

    public IUnitSerializable toIUnitSerializable()
    {
        return this;
    }

    public int getObjectId()
    {
        return objectId;
    }

    public JVMInstance getInstance()
    {
        return JVMInstance.getFromReference(this);
    }

    public override string ToString()
    {
        return "object" + objectId;
    }
}
