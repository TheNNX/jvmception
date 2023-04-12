public class InstructionReturn : Instruction
{

    private IUnitSerializable returnType;

    public InstructionReturn(IUnitSerializable returnType)
    {
        this.returnType = returnType;
    }

    public override void execute(CallFrame frame)
    {
        if (returnType == null)
        {
            frame.returnFromFrame(null);
            return;
        }

        frame.pop(returnType);
        frame.returnFromFrame(returnType);
    }
}
