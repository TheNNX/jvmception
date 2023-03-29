package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMArrayReference;
import jvmception.jvmtypes.JVMInteger;

public class InstructionAStore extends Instruction {

	private IUnitSerializable param;
	
	public InstructionAStore(IUnitSerializable param) {
		this.param = param;
	}

	@Override
	public void execute(CallFrame frame) {
		JVMArrayReference arrReference = new JVMArrayReference();
		JVMInteger index = new JVMInteger();
		frame.pop(param);
		frame.pop(index);
		frame.pop(arrReference);
		arrReference.setElement(index.intValue(), param);
	}

}
