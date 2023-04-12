
using System;

public class InstructionXYtoZ : Instruction
{
    protected IUnitSerializable p1;
    protected IUnitSerializable p2;

    private Func<IUnitSerializable, IUnitSerializable, IUnitSerializable> ExecuteAction = null;

    protected InstructionXYtoZ(IUnitSerializable p1, IUnitSerializable p2)
    {
        this.p1 = p1;
        this.p2 = p2;
    }

    public InstructionXYtoZ(IUnitSerializable p1, IUnitSerializable p2,
                            Func<IUnitSerializable, IUnitSerializable, IUnitSerializable> action)
        : this(p1, p2)
    {
        this.ExecuteAction = action;
    }

    public override void execute(CallFrame frame)
    {
        frame.pop(p2);
        frame.pop(p1);
        IUnitSerializable result = this.ExecuteAction.Invoke(p1, p2);
        frame.push(result);
    }
}
