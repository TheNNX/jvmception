public class InstructionLookupSwitch : Instruction
{

    public override void execute(CallFrame frame)
    {
        int originalPosition = frame.getCurrentIndex() - 1;
        int currentPosition = frame.getCurrentIndex();
        while (currentPosition % 4 != 0)
        {
            /* Read the padding bytes. */
            frame.getNextByte();
            currentPosition++;
        }

        int def = frame.getNextInt();
        int npairs = frame.getNextInt();

        int result = def + originalPosition;
        JVMInteger keyJvm = new JVMInteger();
        frame.pop(keyJvm);

        /* TODO: do a binary search here  */
        for (int i = 0; i < npairs; i++)
        {
            int match = frame.getNextInt();
            int offset = frame.getNextInt();

            if (match == keyJvm.intValue())
            {
                result = offset + originalPosition;
                break;
            }
        }

        frame.setCurrentIndex(result);
    }
}
