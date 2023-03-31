package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class FieldAttribute {
	private String attributeName;
	
	protected FieldAttribute(String attributeName, int attributeLenght, DataInputStream dis) {
		this.attributeName = attributeName;
	}
	
	public String getAttributeName() {
		return this.attributeName;
	}
	
	protected FieldAttribute(String attributeName) {
		this.attributeName = attributeName;
	}

	private static FieldAttribute loadAttribute(DataInputStream dis, CpInfo[] cpInfo) throws IOException {
		int attributeNameIndex = dis.readUnsignedShort();
		int attributeLength = dis.readInt();
		String attributeName = ((CpUtf8)cpInfo[attributeNameIndex]).toString();
		
		switch (attributeName) {
			default:
				return new UnknownAttribute(attributeName, attributeLength, dis);
			case "Code":
				return new CodeAttribute(attributeLength, cpInfo, dis);
		}
	}
	
	public static FieldAttribute[] loadAttributes(DataInputStream dis, CpInfo[] cpInfo, int attributesCount) throws IOException {
		FieldAttribute[] attributes = new FieldAttribute[attributesCount];
		
		for (int i = 0; i < attributesCount; i++) {
			attributes[i] = FieldAttribute.loadAttribute(dis, cpInfo);
		}
		
		return attributes;
	}
}