package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionReturn extends Instruction {

	private IUnitSerializable returnType;
	
	public InstructionReturn(IUnitSerializable returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public void execute(CallFrame frame) {
		if (returnType == null) {
			frame.returnFromFrame(null);
			return;
		}
		
		frame.pop(returnType);
		frame.returnFromFrame(returnType);
	}
}
