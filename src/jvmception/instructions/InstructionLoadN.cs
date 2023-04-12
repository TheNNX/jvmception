
public class InstructionLoadN : InstructionLoad
{

    public InstructionLoadN(IUnitSerializable param) : base(0, param)
    {
    }

    public override void execute(CallFrame frame)
    {
        int index = frame.getNextByte() & 0xFF;
        frame.getLocal(index, param);
        frame.push(param);
    }
}
