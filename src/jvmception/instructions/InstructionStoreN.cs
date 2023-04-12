public class InstructionStoreN : Instruction
{

    private IUnitSerializable param;

    public InstructionStoreN(IUnitSerializable param)
    {
        this.param = param;
    }

    public override void execute(CallFrame frame)
    {
        int index = frame.getNextByte() & 0xFF;
        frame.pop(param);
        frame.setLocal(index, param);
    }
}
