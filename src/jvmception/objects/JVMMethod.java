package jvmception.objects;

import jvmception.CallFrame;
import jvmception.jvmtypes.IJVMConstPoolType;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.objects.cp.CodeAttribute;
import jvmception.objects.cp.CpClass;
import jvmception.objects.cp.CpMethodRef;
import jvmception.objects.cp.CpNameAndIndex;
import jvmception.objects.cp.CpUtf8;
import jvmception.objects.cp.JVMClassFileLoader;
import jvmception.objects.cp.MethodInfo;

public class JVMMethod extends JVMMember {
	private CodeAttribute codeAttribute;
	private IJVMConstPoolType[] constPool;
	private String descriptor;
	private JVMInterface owner;
	private int argumentsSize;
	
	public JVMMethod(MethodInfo methodInfo, IJVMConstPoolType[] constPool, JVMInterface owner) {
		super(methodInfo.getName(), Visibility.fromAccessMask(methodInfo.getAccessFlags()));
		this.codeAttribute = methodInfo.getCodeAttribute();
		this.descriptor = methodInfo.getDescriptor();
		this.constPool = constPool;
		this.owner = owner;
		this.argumentsSize = calculateArgumentsSize();
	}
	
	public CallFrame invokeMethod(CallFrame callerFrame, IUnitSerializable[] params) {
		CallFrame newFrame = new CallFrame(callerFrame, constPool, this);
		
		for (int i = 0; i < params.length; i++)
			newFrame.setLocal(i, params[i]);
		
		callerFrame.setChildFrame(newFrame);
		return newFrame;
	}

	public String getDescriptor() {
		return descriptor;
	}
	
	public static JVMMethod getMethodFromCpIndex(CallFrame frame, int index) throws ClassNotFoundException {
		CpMethodRef cpMethod = (CpMethodRef)frame.getConst(index);
		int nameAndTypeIndex = cpMethod.getNameAndTypeIndex();
		int classIndex = cpMethod.getClassIndex();
		
		CpClass cpClass = (CpClass)frame.getConst(classIndex);
		CpNameAndIndex nameAndType = (CpNameAndIndex)frame.getConst(nameAndTypeIndex);
		CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
		CpUtf8 fieldName = (CpUtf8)frame.getConst(nameAndType.getNameIndex());
		CpUtf8 descriptor = (CpUtf8)frame.getConst(nameAndType.getDescriptorIndex());
		
		JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
		JVMMethod method = theClass.getMethod(fieldName.getString(), descriptor.getString());
		return method;
	}
	
	public int calculateArgumentsSize() {
		String descriptor = this.getDescriptor();
		String preParsed = descriptor, preParsed2 = descriptor;
		
		do {
			preParsed2 = preParsed;
			preParsed = preParsed.replaceFirst("L[^;]*;", "L");
		} while(!preParsed.equals(preParsed2));
		
		preParsed = preParsed.substring(preParsed.indexOf('(') + 1, preParsed.lastIndexOf(')'));
		
		preParsed = preParsed.replaceAll("\\[.", "L");
		preParsed = preParsed.replaceAll("\\[", "");
		
		int size = 0;
		for (int i = 0; i < preParsed.length(); i++) {
			if (preParsed.charAt(i) == 'D' || preParsed.charAt(i) == 'J')
				size+=2;
			else 
				size++;
		}
		
		return size;
	}

	public int getArgumentsSize() {
		return argumentsSize;
	}
	
	public byte[] getCode() {
		return this.codeAttribute.getCode();
	}

	public int getMaxStack() {
		return this.codeAttribute.getMaxStack();
	}
	
	public int getMaxLocals() {
		return this.codeAttribute.getMaxLocals();
	}
	
	@Override
	public String toString() {
		return this.getDescriptor() + " " + this.getMemberName();
	}

	public JVMInterface getOwner() {
		return owner;
	}
}
