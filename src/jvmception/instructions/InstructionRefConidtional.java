package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMReference;

public class InstructionRefConidtional extends Instruction {

	private boolean inverse;
	
	public InstructionRefConidtional(boolean inverse) {
		this.inverse = inverse;
	}
	
	@Override
	public void execute(CallFrame frame) {
		int originalPc = frame.getCurrentIndex() - 1;
		JVMReference ref1 = new JVMReference();
		JVMReference ref2 = new JVMReference();
		frame.pop(ref2);
		frame.pop(ref1);
		
		short branchOffset = frame.getNextShort();;
		
		if ((ref2 == ref1) != inverse) {
			frame.jumpToBranchOffset(originalPc, branchOffset);
		}
	}

}
