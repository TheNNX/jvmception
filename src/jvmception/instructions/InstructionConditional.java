package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMInteger;

public abstract class InstructionConditional extends Instruction {

	protected abstract boolean checkCondition(int i);
	
	@Override
	public void execute(CallFrame frame) {
		JVMInteger i = new JVMInteger();
		short branchOffset = frame.getNextShort();
		frame.pop(i);
		
		if (this.checkCondition(i.intValue()))
			frame.jumpToBranchOffset(branchOffset);
	}

}
