using System;

public class InstructionConditional : Instruction
{

    protected Func<int, bool> CheckCondition = null;

    protected InstructionConditional()
    {
    }

    public InstructionConditional(Func<int, bool> CheckCondition)
    {
        this.CheckCondition = CheckCondition;
    }

    public override void execute(CallFrame frame)
    {
        int originalPc = frame.getCurrentIndex() - 1;
        JVMInteger i = new JVMInteger();
        short branchOffset = frame.getNextShort();
        frame.pop(i);

        if (CheckCondition.Invoke(i.intValue()))
            frame.jumpToBranchOffset(originalPc, branchOffset);
    }
}
