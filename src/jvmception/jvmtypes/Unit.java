package jvmception.jvmtypes;

public class Unit implements IUnitSerializable{
	private byte[] data = new byte[4];
	
	public Unit(byte[] data) {
		this.data = data.clone();
	}
	
	public Unit(int data) {
		this.setInt(data);
	}
	
	public Unit() {
		
	}

	public byte getByte(int idx) {
		return this.data[idx];
	}
	
	public void setByte(int idx, byte data) {
		this.data[idx] = data;
	}

	public int getInt() {
		return (data[0] << 24) | (data[1] << 16) | (data[2] << 8) | (data[0]);
	}
	
	public void setInt(int data) {
		this.data[0] = (byte)((data & 0xFF000000) >> 24);
		this.data[1] = (byte)((data & 0xFF0000) >> 16);
		this.data[2] = (byte)((data & 0xFF00) >> 8);
		this.data[3] = (byte)((data & 0xFF));
	}
	
	@Override
	public Unit[] serialize() {
		return new Unit[] {new Unit(this.data)};
	}

	@Override
	public void deserialize(Unit[] data) {
		this.data = data[0].data.clone();
	}

	@Override
	public int getSerializedSize() {
		return 1;
	}
}