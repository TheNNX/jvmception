package jvmception.jvmtypes;

public class JVMDouble extends JVMFloatingNumber implements IJVMConstPoolType {

	private double data;
	private JVMLong longConverter = new JVMLong();
	
	public JVMDouble() {
		this.data = 0.0;
	}
	
	public JVMDouble(double data) {
		this.data = data;
	}
	
	public double getData() {
		return data;
	}
	
	public void setData(double data) {
		this.data = data;
	}
	
	@Override
	public Unit[] serialize() {
		longConverter.setData(Double.doubleToLongBits(data));
		return longConverter.serialize();
	}

	@Override
	public void deserialize(Unit[] data) {
		longConverter.deserialize(data);
		this.data = longConverter.longValue();
	}

	@Override
	public int getSerializedSize() {
		return 2;
	}
	
	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}

	@Override
	public int intValue() {
		return (int) getData();
	}

	@Override
	public long longValue() {
		return (long) getData();
	}

	@Override
	public float floatValue() {
		return (float) getData();
	}

	@Override
	public double doubleValue() {
		return getData();
	}

	@Override
	public void set(JVMNumber other) {
		this.data = other.doubleValue();
	}
}
