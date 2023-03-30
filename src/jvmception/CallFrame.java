package jvmception;

import jvmception.jvmtypes.IJVMConstPoolType;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.Unit;

public class CallFrame {
	private int operandStackLocation;
	private Unit[] operandStack;
	private Unit[] locals;
	private CallFrame parent;
	private CallFrame child;
	private boolean isReturnPending;
	private IUnitSerializable returnValue;
	private int currentIndex;
	private byte[] program;
	private final IJVMConstPoolType[] constPool;
	
	public CallFrame(int maxStack, int maxLocals, CallFrame parent, IJVMConstPoolType[] constPool,
			byte[] methodCode) {
		operandStackLocation = 0;
		operandStack = new Unit[maxStack];
		locals = new Unit[maxLocals];
		this.parent = parent;
		this.isReturnPending = false;
		returnValue = null;
		this.program = methodCode;
		this.constPool = constPool;
		this.child = null;
	}
	
	public short getNextShort() {
		return (short) ((getNextByte() << 8) | getNextByte());
	}
	
	public int getNextInt() {
		return (int)((getNextShort() << 16) | getNextShort());
	}
	
	public boolean getIsReturnPending() {
		return this.isReturnPending;
	}
	
	public IUnitSerializable getReturnValue() {
		return returnValue;
	}
	
	public void returnFromFrame(IUnitSerializable retval) {
		this.returnValue = retval;
		this.isReturnPending = true;
	}
	
	public void push(IUnitSerializable us) {
		Unit[] serialized = us.serialize();
		for (Unit i : serialized) {
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
	}
	
	public void getLocal(int idx, IUnitSerializable to) {
		Unit[] units = new Unit[to.getSerializedSize()];
		for (int i = 0; i < units.length; i++) {
			units[i] = this.locals[i + idx];
		}
		to.deserialize(units);
	}
	
	public void setLocal(int idx, IUnitSerializable sr) {
		Unit[] units = sr.serialize();
		for (int i = 0; i < units.length; i++) {
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
		return program[currentIndex++];
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
}
