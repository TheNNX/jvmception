package jvmception.objects;

import java.util.ArrayDeque;
import java.util.Hashtable;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMReference;
import jvmception.jvmtypes.Unit;

public class JVMInstance {
	private static Hashtable<Integer, JVMInstance> theHeap = new Hashtable<Integer, JVMInstance>();
	private static int nextGcSweepNumber = 1;
	private static int currentObjectId = 1;
	
	private JVMClass baseClass;
	private int objectId;
	private int lastGcSweepNumber;
	private boolean collectable;
	private CallFrame ownerFrame;
	
	private Hashtable<JVMField, Unit[]> fieldStorage;
	
	private void init(JVMClass baseClass, CallFrame ownerFrame) {
		this.baseClass = baseClass;
		this.fieldStorage = new Hashtable<>();

		for (int i = 0; i < baseClass.getFields().length; i++) {
			/* For each non-static field, allocate the storage in the object instance. */
			
			if (baseClass.getFields()[i].isStatic() == false) {
				fieldStorage.put(
						baseClass.getFields()[i], 
						baseClass.getFields()[i].getContainedType().serialize());
			}
		}
		lastGcSweepNumber = 0;
		collectable = false;
		
		registerInstance();
		
		this.ownerFrame = ownerFrame;
	}
	
	public JVMInstance(JVMClass baseClass, CallFrame ownerFrame) {
		init(baseClass, ownerFrame);
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
	
	public void markAsCollectable() {
		this.collectable = true;
		this.ownerFrame = null;
	}
	
	public Unit[] getFieldStorage(JVMField field) {
		return this.fieldStorage.get(field);
	}

	public JVMReference createReference() {
		return new JVMReference(this.objectId);
	}
	
	private static void markAsUsed(JVMInstance instance) {
		if (instance == null)
			return;
		
		instance.lastGcSweepNumber = nextGcSweepNumber;
		
		JVMField[] fields = instance.baseClass.getFields();
		for (JVMField field : fields) {
			if (field.isStatic())
				continue;
			
			if (!(field.getContainedType() instanceof JVMReference))
				continue;
			
			if (((JVMReference) field.get(instance)).getInstance() == null)
				continue;
			
			if (((JVMReference) field.get(instance)).getInstance().lastGcSweepNumber >= nextGcSweepNumber) 
				continue;
			
			markAsUsed(((JVMReference) field.get(instance)).getInstance());
		}
	}
	
	public static void doGarbageCollection() {
		for (Integer k : theHeap.keySet()) {
			JVMInstance instance = theHeap.get(k);
			if (instance.collectable == false) {
				markAsUsed(instance);
			}
		}
		
		JVMField[] staticFields = JVMField.getStaticFields();
		for (JVMField field : staticFields) {
			if (field.getContainedType() instanceof JVMReference) {
				markAsUsed(((JVMReference) field.get(null)).getInstance());
			}
		}
		
		ArrayDeque<Integer> removeQueue = new ArrayDeque<>();
		for (Integer k : theHeap.keySet()) {
			JVMInstance instance = theHeap.get(k);
			if (instance.lastGcSweepNumber < nextGcSweepNumber) {
				removeQueue.add(k);
			}
		}
		
		for (Integer i : removeQueue) {
			theHeap.remove(i);
		}
		
		nextGcSweepNumber++;
	}

	public CallFrame getOwnerFrame() {
		return ownerFrame;
	}

	public void setOwnerFrame(CallFrame frame) {
		this.ownerFrame = frame;
	}

	public int getObjectId() {
		return this.objectId;
	}
}
