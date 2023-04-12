public class InstructionConst : Instruction
{

    private readonly IUnitSerializable constValue;

    public InstructionConst(IUnitSerializable constValue)
    {
        this.constValue = constValue;
    }

    public override void execute(CallFrame frame)
    {
        frame.push(constValue);
    }
}
