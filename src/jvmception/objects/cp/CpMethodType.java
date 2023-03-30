package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;

public class CpMethodType extends CpInfo {
	private int descriptorIndex;
	
	public CpMethodType(DataInputStream dis) throws IOException {
		descriptorIndex = dis.readUnsignedShort();
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return JVMReference.NULL_REFERENCE;
	}
}


