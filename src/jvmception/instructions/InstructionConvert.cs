
public class InstructionConvert : Instruction
{

    private JVMNumber param1;
    private JVMNumber param2;

    public InstructionConvert(JVMNumber param1, JVMNumber param2)
    {
        this.param1 = param1;
        this.param2 = param2;
    }

    public override void execute(CallFrame frame)
    {
        frame.pop(param1);
        param2.set(param1);
        frame.push(param2);
    }
}
