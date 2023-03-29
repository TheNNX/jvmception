package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionLoad extends Instruction {

	private int index;
	IUnitSerializable param;
	
	public InstructionLoad(int index, IUnitSerializable param) {
		this.index = index;
		this.param = param;
	}
	
	@Override
	public void execute(CallFrame frame) {
		frame.getLocal(index, param);
		frame.push(param);
	}

}
