using System;
using System.Runtime.CompilerServices;

public class Instruction
{
    private Action<CallFrame> ExecuteAction = null;

    protected Instruction()
    {
    }

    public Instruction(Action<CallFrame> action)
    {
        ExecuteAction = action;
    }

    public virtual void execute(CallFrame frame)
    {
        if (ExecuteAction != null)
        {
            ExecuteAction.Invoke(frame);
        }
    }
}