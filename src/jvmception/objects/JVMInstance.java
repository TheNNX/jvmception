package jvmception.objects;

import java.util.Hashtable;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMReference;
import jvmception.jvmtypes.Unit;

public class JVMInstance {
	private static Hashtable<Integer, JVMInstance> theHeap = new Hashtable<Integer, JVMInstance>();
	private static int currentObjectId = 1;
	
	private int numberOfReferences;
	private JVMClass baseClass;
	private int objectId;
	
	private Hashtable<JVMField, Unit[]> fieldStorage;
	
	public JVMInstance(JVMClass baseClass) {
		this.numberOfReferences = 0;
		this.baseClass = baseClass;
		this.fieldStorage = new Hashtable<>();
		for (int i = 0; i < baseClass.getFields().length; i++) {
			/* For each non-static field, allocate the storage in the object instance. */
			if (baseClass.getFields()[i].isStatic() == false) {
				/* FIXME: This is not optimal - not every primitive type needs 2 units. */
				Unit[] storage =  new Unit[2];
				fieldStorage.put(baseClass.getFields()[i], storage);
			}
		}
		registerInstance();
	}
	
	public JVMReference addReference() {
		numberOfReferences++;
		return new JVMReference(this.objectId);
	}
	
	public void removeReference() {
		numberOfReferences--;
		if (numberOfReferences == 0) {
			unregisterInstance();
		}
	}
	
	public static JVMInstance getFromReference(JVMReference reference) {
		return theHeap.get(reference.getObjectId());
	}
	
	private void registerInstance() {
		while (theHeap.containsKey(Integer.valueOf(currentObjectId))) {
			currentObjectId++;
		}
		theHeap.put(Integer.valueOf(currentObjectId), this);
		objectId = currentObjectId;
	}
	
	private void unregisterInstance() {
		theHeap.remove(Integer.valueOf(this.objectId));
	}
	
	public Unit[] getFieldStorage(JVMField field) {
		return this.fieldStorage.get(field);
	}
}
