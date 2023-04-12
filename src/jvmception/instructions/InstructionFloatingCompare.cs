
using System;

public class InstructionFloatingCompare : Instruction
{

    private JVMFloatingNumber param1;
    private JVMFloatingNumber param2;

    private bool direction;

    public InstructionFloatingCompare(JVMFloatingNumber param1, JVMFloatingNumber param2, bool direction)
    {
        this.param1 = param1;
        this.param2 = param2;
        this.direction = direction;
    }

    public override void execute(CallFrame frame)
    {
        frame.pop(param2);
        frame.pop(param1);
        JVMInteger result = new JVMInteger();

        if (direction == false)
        {
            if (param1.doubleValue() < param2.doubleValue())
            {
                result.setData(1);
            }
            else if (Double.IsNaN(param1.doubleValue()) || Double.IsNaN(param2.doubleValue()))
            {
                result.setData(-1);
            }
        }
        else
        {
            if (param1.doubleValue() > param2.doubleValue())
            {
                result.setData(-1);
            }
            else if (Double.IsNaN(param1.doubleValue()) || Double.IsNaN(param2.doubleValue()))
            {
                result.setData(1);
            }
        }

        frame.push(result);
    }
}
