package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class UnknownAttribute extends FieldAttribute {

	public UnknownAttribute(String attributeName, int attributeLenght, DataInputStream dis) throws IOException {
		super(attributeName, attributeLenght, dis);
		dis.readNBytes(attributeLenght);
	}
}