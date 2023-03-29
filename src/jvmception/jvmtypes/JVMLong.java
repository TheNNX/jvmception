package jvmception.jvmtypes;

public class JVMLong extends JVMNumber implements IJVMConstPoolType{

	private long data;
	
	public JVMLong() {
		data = 0;
	}
	
	public JVMLong(long data) {
		this.data = data;
	}
	
	@Override
	public Unit[] serialize() {
		Unit[] result = new Unit[2];
		JVMInteger view = new JVMInteger((int) (data >> 32));
		result[0] = view.serialize()[0];
		view.setData((int)(data & 0xFFFFFFFF));
		result[1] = view.serialize()[0];
		return result;
	}

	@Override
	public void deserialize(Unit[] data) {
		JVMInteger h1 = new JVMInteger(0);
		JVMInteger h2 = new JVMInteger(0);
		h1.deserialize(new Unit[] {data[0]});
		h2.deserialize(new Unit[] {data[1]});
		this.data = (((long)h1.intValue()) << 32) | (long)h2.intValue();
	}

	@Override
	public int getSerializedSize() {
		return 2;
	}

	public void setData(long doubleToLongBits) {
		this.data = doubleToLongBits;
	}
	
	@Override
	public IUnitSerializable toIUnitSerializable() {
		return this;
	}

	@Override
	public int intValue() {
		return (int) this.data;
	}

	@Override
	public long longValue() {
		return this.data;
	}

	@Override
	public float floatValue() {
		return this.data;
	}

	@Override
	public double doubleValue() {
		return this.data;
	}

	@Override
	public void set(JVMNumber other) {
		this.data = other.longValue();
	}

}
