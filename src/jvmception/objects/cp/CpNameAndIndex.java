package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;

public class CpNameAndIndex extends CpInfo {
	int nameIndex;
	int descriptorIndex;
	
	public CpNameAndIndex (DataInputStream dis) throws IOException {
		nameIndex = dis.readUnsignedShort();
		descriptorIndex = dis.readUnsignedShort();
	}
	
	public int getNameIndex() {
		return nameIndex;
	}
	
	public int getDescriptorIndex() {
		return descriptorIndex;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return JVMReference.NULL_REFERENCE;
	}
}
