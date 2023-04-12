
using System.Collections.Generic;

public class CallFrame {
	private int operandStackLocation;
	private Unit[] operandStack;
	private Unit[] locals;
	private CallFrame parent;
	private CallFrame child;
	private bool isReturnPending;
	private IUnitSerializable returnValue;
	private int currentIndex;
	private readonly IJVMConstPoolType[] constPool;
	private List<JVMInstance> createdInstances;
	private JVMMethod method;
	
	public CallFrame(CallFrame parent, IJVMConstPoolType[] constPool, JVMMethod method) {
		operandStackLocation = 0;
		operandStack = new Unit[method.getMaxStack()];
		locals = new Unit[method.getMaxLocals()];
		this.parent = parent;
		this.isReturnPending = false;
		returnValue = null;
		this.constPool = constPool;
		this.child = null;
		this.createdInstances = new List<JVMInstance>();
		this.method = method;
	}
	
	public short getNextShort() {
		return (short) ((getNextByte() << 8) | getNextByte());
	}
	
	public int getNextInt() {
		return (int)((getNextShort() << 16) | getNextShort());
	}
	
	public bool getIsReturnPending() {
		return this.isReturnPending;
	}
	
	public IUnitSerializable getReturnValue() {
		return returnValue;
	}
	
	public void returnFromFrame(IUnitSerializable retval) {
		this.returnValue = retval;
		this.isReturnPending = true;
		
		/* Make sure the return value is not garbage collected, by
		 * by moving the "ownership" of the object to the calling
		 * frame. */
		if (retval != null && retval is JVMReference) {
			JVMInstance retInstance = JVMInstance.getFromReference((JVMReference) retval);
			
			retInstance.setOwnerFrame(this.parent);
			createdInstances.Remove(retInstance);
			this.parent.createdInstances.Add(retInstance);
		}
		
		/* Mark every object as collectable - if it isn't referenced
		 * in any graph containing a non-collectable, it will be garbage collected. */
		foreach (JVMInstance instance in createdInstances) {
			instance.markAsCollectable();
		}
		
		/* Collect objects in disconnected graphs. */
		JVMInstance.doGarbageCollection();
		
		if (retval != null)
		{
			this.parent.push(retval);
		}
	}
	
	public void push(IUnitSerializable us) {
		//System.Console.WriteLine(this.ToString() + " - Pusing "+us.ToString());
		Unit[] serialized = us.serialize();
		foreach (Unit i in serialized) {
			this.pushUnit(i);
		}
	}
	
	public void pop(IUnitSerializable to) {
		int size = to.getSerializedSize();
		Unit[] data = new Unit[size];
		for (int i = size - 1; i >= 0; i--) {
			data[i] = this.popUnit();
		}
		to.deserialize(data);
		//System.Console.WriteLine(this.ToString() + " - Popping "+to.ToString());
	}
	
	public void getLocal(int idx, IUnitSerializable to) {
		Unit[] units = new Unit[to.getSerializedSize()];
		for (int i = 0; i < units.Length; i++) {
			units[i] = this.locals[i + idx];
		}
		to.deserialize(units);
	}
	
	public void setLocal(int idx, IUnitSerializable sr) {
		Unit[] units = sr.serialize();
		for (int i = 0; i < units.Length; i++) {
			this.locals[i + idx] = units[i];
		}
	}
	
	private Unit popUnit() {
		operandStackLocation--;
		return operandStack[operandStackLocation];
	}
	
	private void pushUnit(Unit u) {
		operandStack[operandStackLocation] = u;
		operandStackLocation++;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public byte getNextByte() {
		return this.method.getCode()[currentIndex++];
	}

	public void setCurrentIndex(int idx) {
		this.currentIndex = idx;
	}
	
	public IJVMConstPoolType getConst(int idx) {
		return constPool[idx];
	}

	public void jumpToBranchOffset(int originalPc, short branchOffset) {
		this.currentIndex = originalPc + branchOffset;
	}
	
	public void setChildFrame(CallFrame c) {
		this.child = c;
	}
	
	public CallFrame getChildFrame() {
		return this.child;
	}

	public CallFrame getParent() {
		return this.parent;
	}
	
	public JVMInstance createOwnedInstance(JVMClass jvmClass) {
		JVMInstance instance = new JVMInstance(jvmClass, this);
		this.createdInstances.Add(instance);
		return instance;
	}

	public string getString() {
		return method.ToString() + "+" + (this.currentIndex - 1);
	}

	public override string ToString() {
		return this.getString();
	}
}
