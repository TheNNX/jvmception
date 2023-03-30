package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMFloat;

public class CpFloat extends CpInfo {
	private float floatingPoint;
	
	public CpFloat(DataInputStream dis) throws IOException {
		floatingPoint = dis.readFloat();
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return new JVMFloat(floatingPoint);
	}
}
