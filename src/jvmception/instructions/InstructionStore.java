package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionStore extends Instruction {

	private IUnitSerializable param;
	private int index;
	
	public InstructionStore(int index, IUnitSerializable param) {
		this.param = param;
		this.index = index;
	}
	
	@Override
	public void execute(CallFrame frame) {
		frame.pop(param);
		frame.setLocal(index, param);
	}
}
