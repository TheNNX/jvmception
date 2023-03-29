package jvmception.jvmtypes;

public interface IUnitSerializable {
	public Unit[] serialize();
	public void deserialize(Unit[] data);
	public int getSerializedSize();
}
