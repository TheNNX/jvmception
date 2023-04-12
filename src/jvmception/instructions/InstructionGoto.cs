public class InstructionGoto : Instruction
{

    public override void execute(CallFrame frame)
    {
        int originalPc = frame.getCurrentIndex() - 1;
        short branchOffset = frame.getNextShort();
        frame.jumpToBranchOffset(originalPc, branchOffset);
    }
}
