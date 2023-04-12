class InstructionJsr : InstructionGoto
{
    public override void execute(CallFrame frame)
    {
        JVMInteger returnAddress = new JVMInteger(frame.getCurrentIndex());
        frame.push(returnAddress);
        base.execute(frame);
    }
}