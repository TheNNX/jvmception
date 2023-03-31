package jvmception.jvmtypes;

public class JVMFloat extends JVMFloatingNumber implements IJVMConstPoolType {

	private float data;
	private JVMInteger integerConverter = new JVMInteger(0);
	
	public JVMFloat() {
		this.data = 0;
	}
	
	public JVMFloat(float data) {
		this.data = data;
	}
	
	@Override
	public Unit[] serialize() {
		integerConverter.setData(Float.floatToIntBits(data));
		return integerConverter.serialize();
	}

	@Override
	public void deserialize(Unit[] data) {
		integerConverter.deserialize(data);
		this.data = Float.intBitsToFloat(integerConverter.intValue());
	}

	@Override
	public int getSerializedSize() {
		return 1;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}
	
	public void setData(float data) {
		this.data = data;
	}

	@Override
	public int intValue() {
		return (int) data;
	}

	@Override
	public long longValue() {
		return (long) data;
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
		this.data = other.floatValue();
	}

	@Override
	public String toString() {
		return ""+this.floatValue();
	}
}
