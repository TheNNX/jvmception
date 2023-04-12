
public class InstructionLoad : Instruction
{

    private int index;
    protected IUnitSerializable param;

    public InstructionLoad(int index, IUnitSerializable param)
    {
        this.index = index;
        this.param = param;
    }

    public override void execute(CallFrame frame)
    {
        frame.getLocal(index, param);
        frame.push(param);
    }
}
