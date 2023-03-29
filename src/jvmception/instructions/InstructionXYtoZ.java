package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public abstract class InstructionXYtoZ extends Instruction{

	protected IUnitSerializable p1;
	protected IUnitSerializable p2;
	
	public InstructionXYtoZ(IUnitSerializable p1, IUnitSerializable p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	protected abstract IUnitSerializable performOperation();
	
	@Override
	public void execute(CallFrame frame) {		
		frame.pop(p1);
		frame.pop(p2);
		IUnitSerializable result = this.performOperation();
		frame.push(result);
	}
	
}
