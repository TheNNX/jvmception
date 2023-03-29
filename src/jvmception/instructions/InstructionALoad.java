package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMArrayReference;
import jvmception.jvmtypes.JVMInteger;

public class InstructionALoad extends Instruction {

	private IUnitSerializable param;
	
	public InstructionALoad(IUnitSerializable param) {
		this.param = param;
	}
	
	@Override
	public void execute(CallFrame frame) {
		JVMArrayReference arrReference = new JVMArrayReference();
		JVMInteger index = new JVMInteger();
		frame.pop(index);
		frame.pop(arrReference);
		
		arrReference.getElement(index.intValue(), param);
		frame.push(param);
	}
}
