package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMInteger;

public class CpInteger extends CpInfo {
	private int integer;
	
	public CpInteger(DataInputStream dis) throws IOException {
		integer = dis.readInt();
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return new JVMInteger(integer);
	}
}
