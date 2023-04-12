
public class InstructionALoad : Instruction
{

    private IUnitSerializable param;

    public InstructionALoad(IUnitSerializable param)
    {
        this.param = param;
    }

    public override void execute(CallFrame frame)
    {
        JVMArrayReference arrReference = new JVMArrayReference();
        JVMInteger index = new JVMInteger();
        frame.pop(index);
        frame.pop(arrReference);

        arrReference.getElement(index.intValue(), param);
        frame.push(param);
    }
}
