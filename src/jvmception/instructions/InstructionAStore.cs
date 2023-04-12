public class InstructionAStore : Instruction
{

    private IUnitSerializable param;

    public InstructionAStore(IUnitSerializable param)
    {
        this.param = param;
    }

    public override void execute(CallFrame frame)
    {
        JVMArrayReference arrReference = new JVMArrayReference();
        JVMInteger index = new JVMInteger();
        frame.pop(param);
        frame.pop(index);
        frame.pop(arrReference);
        arrReference.setElement(index.intValue(), param);
    }
}
