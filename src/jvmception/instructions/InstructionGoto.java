package jvmception.instructions;

import jvmception.CallFrame;

public class InstructionGoto extends Instruction {
	
	@Override
	public void execute(CallFrame frame) {
		short branchOffset = frame.getNextShort();
		frame.jumpToBranchOffset(branchOffset);
	}

}
