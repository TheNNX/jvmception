package jvmception.jvmtypes;

public class JVMReference implements IUnitSerializable, IJVMConstPoolType{
	private int objectId;
	public static final JVMReference NULL_REFERENCE = new JVMReference(0);
	
	public JVMReference(int objectId) {
		this.objectId = objectId;
	}
	
	public JVMReference() {
		this.objectId = 0;
	}
	
	@Override
	public Unit[] serialize() {
		JVMInteger serialize = new JVMInteger(this.objectId);
		return serialize.serialize();
	}

	@Override
	public void deserialize(Unit[] data) {
		JVMInteger deserialize = new JVMInteger();
		deserialize.deserialize(data);
		this.objectId = deserialize.intValue();
	}

	@Override
	public int getSerializedSize() {
		return 1;
	}
	
	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}

	public Object getObjectId() {
		return objectId;
	}
}
