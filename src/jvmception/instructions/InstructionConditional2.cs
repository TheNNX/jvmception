
using System;

public class InstructionConditional2 : InstructionConditional
{

    protected InstructionConditional2()
    {
    }

    public InstructionConditional2(Func<int, bool> CheckCondition) : base(CheckCondition)
    {
    }

    public override void execute(CallFrame frame)
    {
        int originalPc = frame.getCurrentIndex() - 1;
        JVMInteger i1 = new JVMInteger(), i2 = new JVMInteger();

        frame.pop(i2);
        frame.pop(i1);

        short address = frame.getNextShort();

        if (this.CheckCondition.Invoke(i1.intValue() - i2.intValue()))
            frame.jumpToBranchOffset(originalPc, address);
    }
}
