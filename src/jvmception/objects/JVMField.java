package jvmception.objects;

import java.util.ArrayList;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.Unit;
import jvmception.objects.cp.CpClass;
import jvmception.objects.cp.CpFieldRef;
import jvmception.objects.cp.CpNameAndIndex;
import jvmception.objects.cp.CpUtf8;
import jvmception.objects.cp.FieldInfo;
import jvmception.objects.cp.JVMClassFileLoader;

public class JVMField extends JVMMember {

	private String descriptor;
	private Unit[] staticStorage = null;
	private static ArrayList<JVMField> staticFields = new ArrayList<>();
	private JVMInterface owner;
	
	public JVMField(FieldInfo fieldInfo, JVMInterface owner) {
		super(fieldInfo.getName(), Visibility.fromAccessMask(fieldInfo.getAccessFlags()));
		descriptor = fieldInfo.getDescriptor();
		
		if ((fieldInfo.getAccessFlags() & 0x0008) != 0) {
			staticStorage = getContainedType().serialize();
			staticFields.add(this);
		}
		
		this.owner = owner;
	}

	public static JVMField[] getStaticFields() {
		JVMField[] result = new JVMField[staticFields.size()];
		for (int i = 0; i < result.length; i++)
			result[i] = staticFields.get(i);
		return result;
	}

	public String getDescriptor() {
		return descriptor;
	}
	
	public IUnitSerializable getContainedType() {
		return IUnitSerializable.getTypeFromCode(descriptor.charAt(0));
	}

	public void set(JVMInstance instance, IUnitSerializable data) {
		Unit[] dst;
		
		if (instance == null) {
			dst = this.staticStorage;
		} else {
			dst = instance.getFieldStorage(this);
		}
		
		Unit[] copy = data.serialize();
		for (int i = 0; i < dst.length; i++) {
			dst[i] = copy[i];
		}
	}
	
	public IUnitSerializable get(JVMInstance instance) {
		Unit[] src;
		
		if (instance == null) {
			src = this.staticStorage;
		} else {
			src = instance.getFieldStorage(this);
		}
		
		IUnitSerializable result = this.getContainedType();
		result.deserialize(src);
		return result;
	}

	public boolean isStatic() {
		return this.staticStorage != null;
	}

	public static JVMField getFieldFromCpIndex(CallFrame frame, int index) throws ClassNotFoundException {
		CpFieldRef cpField = (CpFieldRef)frame.getConst(index);
		int nameAndTypeIndex = cpField.getNameAndTypeIndex();
		int classIndex = cpField.getClassIndex();
		
		CpClass cpClass = (CpClass)frame.getConst(classIndex);
		CpNameAndIndex nameAndType = (CpNameAndIndex)frame.getConst(nameAndTypeIndex);
		CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
		CpUtf8 fieldName = (CpUtf8)frame.getConst(nameAndType.getNameIndex());
		CpUtf8 descriptor = (CpUtf8)frame.getConst(nameAndType.getDescriptorIndex());
		
		JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
		JVMField field = theClass.getField(fieldName.getString(), descriptor.getString());
		return field;
	}

	public JVMInterface getOwner() {
		return owner;
	}
}
