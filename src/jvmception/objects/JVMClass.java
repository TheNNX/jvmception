package jvmception.objects;

import jvmception.CallFrame;
import jvmception.jvmtypes.IJVMConstPoolType;
import jvmception.objects.cp.CpClass;
import jvmception.objects.cp.CpUtf8;
import jvmception.objects.cp.JVMClassFileLoader;

public class JVMClass extends JVMInterface {	
	private JVMClass baseClass;
	private JVMField[] fields = null;
	private IJVMConstPoolType[] constPool;
	
	public JVMClass(String name, JVMClass baseClass, JVMInterface[] interfaces, Visibility visibility, IJVMConstPoolType[] constPool) {
		super(name, interfaces, visibility);
		this.baseClass = baseClass;
		this.constPool = constPool;
	}
	
	public JVMField[] getFields() {
		return this.fields;
	}
	
	public boolean setFields(JVMField[] fields) {
		if (this.fields != null)
			return false;
		this.fields = fields;
		return true;
	}
	
	public JVMMethod getMethod(String name, String descriptor) {
		for (JVMMethod m : this.getMethods()) {
			if (m.getMemberName().equals(name) && m.getDescriptor().equals(descriptor)) {
				return m;
			}
		}
		if (baseClass != null) {
			return baseClass.getMethod(name, descriptor);
		}
		throw new NoSuchMethodError(name + " " + descriptor);
	}
	
	public JVMField getField(String name, String descriptor) {
		for (JVMField f : this.getFields()) {
			if (f.getMemberName() == name && f.getDescriptor() == descriptor) {
				return f;
			}
		}
		if (baseClass != null) {
			return baseClass.getField(name, descriptor);
		}
		throw new NoSuchFieldError(name + " " + descriptor);
	}

	public IJVMConstPoolType[] getConstPool() {
		return constPool;
	}

	public static JVMClass getClassFromCpIndex(CallFrame frame, int index) throws ClassNotFoundException {
		CpClass cpClass = (CpClass)frame.getConst(index);
		CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
		JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
		return theClass;
	}
}
