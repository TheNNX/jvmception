package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionStoreN extends Instruction {

	private IUnitSerializable param;
	
	public InstructionStoreN(IUnitSerializable param) {
		this.param = param;
	}
	
	@Override
	public void execute(CallFrame frame) {
		int index = frame.getNextByte() & 0xFF;
		frame.pop(param);
		frame.setLocal(index, param);
	}
}
