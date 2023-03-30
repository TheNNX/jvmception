package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.ref.Reference;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;
import jvmception.objects.JVMInstance;

public class CpString extends CpInfo{
	private int stringIndex;
	private JVMReference reference = null;
	private CpInfo[] cpInfo;
	
	public CpString(DataInputStream dis, CpInfo[] cpInfo) throws IOException{
		stringIndex = dis.readUnsignedShort();
		this.cpInfo = cpInfo;
	}
	
	public int getStringIndex() {
		return stringIndex;
	}

	@Override
	public IUnitSerializable toIUnitSerializable() {
		if (reference == null) {
			cpInfo = null;
			reference = (JVMReference) ((CpUtf8)cpInfo[stringIndex]).toIUnitSerializable();
		}
		return reference;
	}
}
