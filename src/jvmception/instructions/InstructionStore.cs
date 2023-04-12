public class InstructionStore : Instruction
{

    private IUnitSerializable param;
    private int index;

    public InstructionStore(int index, IUnitSerializable param)
    {
        this.param = param;
        this.index = index;
    }

    public override void execute(CallFrame frame)
    {
        frame.pop(param);
        frame.setLocal(index, param);
    }
}
