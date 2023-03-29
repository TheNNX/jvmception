package jvmception.jvmtypes;

public class JVMReference implements IUnitSerializable, IJVMConstPoolType{
	@Override
	public Unit[] serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserialize(Unit[] data) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSerializedSize() {
		return 1;
	}
	
	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}
}
