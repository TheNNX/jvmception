package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IJVMConstPoolType;

public abstract class CpInfo implements IJVMConstPoolType{
	public int getCpIncrement() {
		return 1;
	}
	
	public static CpInfo readCpInfo(DataInputStream stream, CpInfo[] cpInfo) throws IOException {
		int tag = stream.readUnsignedByte();
		switch (tag) {
		case 7:
			return new CpClass(stream);
		case 9:
			return new CpFieldRef(stream);
		case 10:
			return new CpMethodRef(stream);
		case 11:
			return new CpInterfaceMethodRef(stream);
		case 8:
			return new CpString(stream, cpInfo);
		case 3:
			return new CpInteger(stream);
		case 4:
			return new CpFloat(stream);
		case 5:
			return new CpLong(stream);
		case 6:
			return new CpDouble(stream);
		case 12:
			return new CpNameAndIndex(stream);
		case 1:
			return new CpUtf8(stream);
		case 15:
			return new CpMethodHandle(stream);
		case 16:
			return new CpMethodType(stream);
		}
		return null;
	}
}

	