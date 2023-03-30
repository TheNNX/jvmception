package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;

public class CpClass extends CpInfo {
	private int nameIndex;
	
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	public CpClass(DataInputStream stream) throws IOException {
		nameIndex = stream.readUnsignedShort();
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return JVMReference.NULL_REFERENCE;
	}
}
