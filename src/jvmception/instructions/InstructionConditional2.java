package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMInteger;

public abstract class InstructionConditional2 extends InstructionConditional {
	
	@Override
	public void execute(CallFrame frame) {
		int originalPc = frame.getCurrentIndex() - 1;
		JVMInteger i1 = new JVMInteger(), i2 = new JVMInteger();
		
		frame.pop(i2);
		frame.pop(i1);
		
		short address = frame.getNextShort();
		
		if (this.checkCondition(i1.intValue() - i2.intValue()))
			frame.jumpToBranchOffset(originalPc, address);
	}
}
