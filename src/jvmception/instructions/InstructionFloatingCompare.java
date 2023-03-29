package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMFloatingNumber;
import jvmception.jvmtypes.JVMInteger;

public class InstructionFloatingCompare extends Instruction {

	private JVMFloatingNumber param1;
	private JVMFloatingNumber param2;
	
	private boolean direction;
	
	public InstructionFloatingCompare(JVMFloatingNumber param1, JVMFloatingNumber param2, 
			boolean direction) {
		this.param1 = param1;
		this.param2 = param2;
		this.direction = direction;
	}
	
	@Override
	public void execute(CallFrame frame) {
		frame.pop(param2);
		frame.pop(param1);
		JVMInteger result = new JVMInteger();
		
		if (direction == false) {
			if (param1.doubleValue() < param2.doubleValue()) {
				result.setData(1);
			}
			else if (Double.isNaN(param1.doubleValue()) || 
					 Double.isNaN(param2.doubleValue())) {
				result.setData(-1);
			}
		}
		else {
			if (param1.doubleValue() > param2.doubleValue()) {
				result.setData(-1);
			}
			else if (Double.isNaN(param1.doubleValue()) || 
					 Double.isNaN(param2.doubleValue())) {
				result.setData(1);
			}
		}
		
		frame.push(result);
	}

}
