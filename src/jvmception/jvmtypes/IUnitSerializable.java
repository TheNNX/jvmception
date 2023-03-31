package jvmception.jvmtypes;

public interface IUnitSerializable {
	public Unit[] serialize();
	public void deserialize(Unit[] data);
	public int getSerializedSize();
	
	public static IUnitSerializable getTypeFromCode(char c) {
		switch (c) {
		case 'B':
		case 'C':
			/* TODO: chars and bytes shlouldn't be ints - handle sign extension etc. */
			return new JVMInteger();
		case 'D':
			return new JVMDouble();
		case 'F':
			return new JVMFloat();
		case 'I':
			return new JVMInteger();
		case 'J':
			return new JVMLong();
		case 'L':
			return new JVMReference();
		case 'S':
			/* TODO: short */
			return new JVMInteger();
		case 'Z':
			/* TODO: boolean */
			return new JVMInteger();
		case '[':
			return new JVMArrayReference();
		default:
			return null;
		}
	}
}
