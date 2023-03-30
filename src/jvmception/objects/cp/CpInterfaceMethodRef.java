package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class CpInterfaceMethodRef extends CpMethodRef {
	public CpInterfaceMethodRef(DataInputStream dis) throws IOException {
		super(dis);
	}
}
