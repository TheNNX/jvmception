package jvmception.objects;

import jvmception.CallFrame;
import jvmception.jvmtypes.IJVMConstPoolType;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.objects.cp.CodeAttribute;
import jvmception.objects.cp.CpClass;
import jvmception.objects.cp.CpFieldRef;
import jvmception.objects.cp.CpMethodRef;
import jvmception.objects.cp.CpNameAndIndex;
import jvmception.objects.cp.CpUtf8;
import jvmception.objects.cp.JVMClassFileLoader;
import jvmception.objects.cp.MethodInfo;

public class JVMMethod extends JVMMember {
	private CodeAttribute codeAttribute;
	private IJVMConstPoolType[] constPool;
	private String descriptor;
	
	public JVMMethod(MethodInfo methodInfo, IJVMConstPoolType[] constPool) {
		super(methodInfo.getName(), Visibility.fromAccessMask(methodInfo.getAccessFlags()));
		this.codeAttribute = methodInfo.getCodeAttribute();
		this.descriptor = methodInfo.getDescriptor();
		this.constPool = constPool;
	}
	
	public CallFrame invokeMethod(CallFrame callerFrame, IUnitSerializable[] params) {
		CallFrame newFrame = new CallFrame(
				codeAttribute.getMaxStack(), 
				codeAttribute.getMaxLocals(), 
				callerFrame,
				constPool,
				codeAttribute.getCode());
		
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
	
	public int getArgumentsSize() {
		/* TODO */
		return 0;
	}

	public byte[] getCode() {
		return this.codeAttribute.getCode();
	}
}
