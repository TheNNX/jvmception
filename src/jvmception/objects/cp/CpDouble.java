package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMDouble;

public  class CpDouble extends CpInfo{
	private double doubleFloating;
	
	public CpDouble(DataInputStream dis) throws IOException {
		doubleFloating = dis.readDouble();
	}
	
	public double getDouble() {
		return doubleFloating;
	}
	
	@Override
	public int getCpIncrement() {
		return 2;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return new JVMDouble(doubleFloating);
	}
}
