package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class CodeAttribute extends FieldAttribute {

	private int maxStack;
	private int maxLocals;
	private int codeLength;
	private byte[] code;
	private FieldAttribute[] attributes;
	
	public CodeAttribute(int attributeLenght, CpInfo[] cpInfo, DataInputStream dis) throws IOException {
		super("Code", attributeLenght, dis);
		maxStack = dis.readUnsignedShort();
		maxLocals = dis.readUnsignedShort();
		codeLength = dis.readInt();
		code = dis.readNBytes(codeLength);
		
		/* TODO: Exceptions */
		int exceptionTableLength = dis.readUnsignedShort();
		dis.readNBytes(8 * exceptionTableLength);
		
		int attributesCount = dis.readUnsignedShort();
		attributes = FieldAttribute.loadAttributes(dis, cpInfo, attributesCount);
	}

	protected CodeAttribute() {
		super("Code");
		this.maxLocals = 1;
		code = new byte[] {(byte)(0xB1)};
	}

	public FieldAttribute[] getAttributes() {
		return attributes;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public byte[] getCode() {
		return code;
	}

	public int getMaxLocals() {
		return maxLocals;
	}

	public int getMaxStack() {
		return maxStack;
	}
}
