package jvmception.instructions;

import jvmception.CallFrame;

public abstract class Instruction {
	public abstract void execute(CallFrame frame) throws Exception;
}
