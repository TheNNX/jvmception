package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class CpMethodRef extends CpFieldRef {
	public CpMethodRef(DataInputStream dis) throws IOException {
		super(dis);
	}
}
