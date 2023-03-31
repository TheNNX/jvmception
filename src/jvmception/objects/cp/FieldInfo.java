package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class FieldInfo{
	private int accessFlags;
	private String name;
	private String descriptor;
	private int attributesCount;
	private FieldAttribute[] attributes;
	
	public FieldInfo(DataInputStream dis, CpInfo[] cpInfo) throws IOException {
		accessFlags = dis.readUnsignedShort();
		int nameIndex = dis.readUnsignedShort();
		int descriptorIndex = dis.readUnsignedShort();
		attributesCount = dis.readUnsignedShort();
		
		name = ((CpUtf8)cpInfo[nameIndex]).toString();
		descriptor = ((CpUtf8)cpInfo[descriptorIndex]).toString();
		
		attributes = FieldAttribute.loadAttributes(dis, cpInfo, attributesCount);
	}
	
	protected FieldInfo(String name, String descriptor) {
		this.name = name;
		this.descriptor = descriptor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescriptor() {
		return descriptor;
	}
	
	public int getAccessFlags() {
		return accessFlags;
	}
	
	public FieldAttribute[] getAttributes() {
		return this.attributes;
	}
}