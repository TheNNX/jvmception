package jvmception.instructions;

import jvmception.CallFrame;
import jvmception.jvmtypes.JVMInteger;

public class InstructionTableSwitch extends Instruction {

	@Override
	public void execute(CallFrame frame) {
		int originalPosition = frame.getCurrentIndex() - 1;
		int currentPosition = frame.getCurrentIndex();
		while(currentPosition % 4 != 0) {
			/* Read the padding bytes. */
			frame.getNextByte();
			currentPosition++;
		}
		
		int def = frame.getNextInt();
		int low = frame.getNextInt();
		int high = frame.getNextInt();
		
		JVMInteger indexJvm = new JVMInteger();
		frame.pop(indexJvm);
		int index = indexJvm.intValue();
		int destination = 0;
		
		if (index > high || index < low) {
			destination = originalPosition + def;
		}
		else {
			frame.setCurrentIndex(currentPosition + 12 + 4 * (index - low));
			destination = frame.getNextInt();
		}
		
		frame.setCurrentIndex(destination);
	}

}
