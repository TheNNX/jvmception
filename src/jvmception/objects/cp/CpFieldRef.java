package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;

public class CpFieldRef extends CpInfo {
	private int classIndex;
	private int nameAndTypeIndex;
	
	public CpFieldRef(DataInputStream stream) throws IOException {
		classIndex = stream.readUnsignedShort();
		nameAndTypeIndex = stream.readUnsignedShort();
	}
	
	public int getClassIndex() {
		return classIndex;
	}
	
	public int getNameAndTypeIndex() {
		return nameAndTypeIndex;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return JVMReference.NULL_REFERENCE;
	}
}
