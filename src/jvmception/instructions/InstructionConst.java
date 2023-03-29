package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionConst extends Instruction {

	private final IUnitSerializable constValue;
	
	public InstructionConst(IUnitSerializable constValue) {
		this.constValue = constValue;
	}
	
	@Override
	public void execute(CallFrame frame) {
		frame.push(constValue);
	}

}
