package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;

public class InstructionLoadN extends InstructionLoad {

	public InstructionLoadN(IUnitSerializable param) {
		super(0, param);
	}

	@Override
	public void execute(CallFrame frame) {
		int index = frame.getNextByte() & 0xFF;
		frame.getLocal(index, param);
		frame.push(param);
	}
}
