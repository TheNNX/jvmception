package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMReference;
import jvmception.objects.JVMInstance;
 
public class CpUtf8 extends CpInfo {
	private String str;
	private JVMReference reference;
	
	public CpUtf8(DataInputStream dis) throws IOException {
		int lenght = dis.readUnsignedShort();
		byte[] data = dis.readNBytes(lenght);
		str = new String(data);
	}
	
	public String getString() {
		return str;
	}
	
	@Override
	public String toString() {
		return str;
	}

	private JVMReference getStringReference() {
		if (reference != null) {
			return reference;
		}
		try {
			JVMInstance instance = new JVMInstance(JVMClassFileLoader.getClassByName("java/lang/String"));
			/* TODO load string or something idk */
			return instance.addReference();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public IUnitSerializable toIUnitSerializable() {
		return reference;
	}
}
