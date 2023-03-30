package jvmception.instructions;

import jvmception.CallFrame;

public class InstructionGoto extends Instruction {
	
	@Override
	public void execute(CallFrame frame) {
		int originalPc = frame.getCurrentIndex() - 1;
		short branchOffset = frame.getNextShort();
		frame.jumpToBranchOffset(originalPc, branchOffset);
	}

}
