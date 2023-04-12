public class InstructionRefConidtional : Instruction
{

    private bool inverse;

    public InstructionRefConidtional(bool inverse)
    {
        this.inverse = inverse;
    }

    public override void execute(CallFrame frame)
    {
        int originalPc = frame.getCurrentIndex() - 1;
        JVMReference ref1 = new JVMReference();
        JVMReference ref2 = new JVMReference();
        frame.pop(ref2);
        frame.pop(ref1);

        short branchOffset = frame.getNextShort();
        ;

        if ((ref2 == ref1) != inverse)
        {
            frame.jumpToBranchOffset(originalPc, branchOffset);
        }
    }
}
