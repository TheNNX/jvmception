package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;

public class CpMethodHandle extends CpInfo {
	private int referenceKind;
	private int referenceIndex;
	
	public CpMethodHandle(DataInputStream dis) throws IOException{
		referenceKind = dis.readUnsignedByte();
		referenceIndex = dis.readUnsignedShort();
	}
	
	public int getRefKind() {
		return referenceKind;
	}
	
	public int getRefIndex() {
		return referenceIndex;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return JVMReference.NULL_REFERENCE;
	}
}
