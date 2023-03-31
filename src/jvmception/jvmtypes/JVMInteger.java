package jvmception.jvmtypes;

public class JVMInteger extends JVMNumber implements IJVMConstPoolType {

	private int data;
	
	public JVMInteger(int data) {
		this.data = data;
	}
	
	public JVMInteger() {
		this.data = 0;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	@Override
	public Unit[] serialize() {
		Unit result = new Unit(this.data);
		return new Unit[] {result};
	}

	@Override
	public void deserialize(Unit[] data) {
		this.data = data[0].getInt();
	}

	@Override
	public int getSerializedSize() {
		return 1;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}

	@Override
	public int intValue() {
		return data;
	}

	@Override
	public long longValue() {
		return data;
	}

	@Override
	public float floatValue() {
		return data;
	}

	@Override
	public double doubleValue() {
		return data;
	}

	@Override
	public void set(JVMNumber other) {
		this.data = other.intValue();
	}

	@Override
	public String toString() {
		return ""+this.intValue();
	}
}
