package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMLong;

public class CpLong extends CpInfo{
	private long longInteger;
	
	public CpLong(DataInputStream dis) throws IOException {
		longInteger = dis.readLong();
	}
	
	public long getLong() {
		return longInteger;
	}
	
	@Override
	public int getCpIncrement() {
		return 2;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		return new JVMLong(longInteger);
	}
}
