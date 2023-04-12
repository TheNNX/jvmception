using System;
using System.IO;

public class CpUtf8 : CpInfo
{
    private string str;
    private JVMReference reference;

    public CpUtf8(BinaryReaderBigEndian dis)
    {
        int lenght = dis.ReadUInt16();
        byte[] data = dis.ReadBytes(lenght);
        str = System.Text.Encoding.ASCII.GetString(data);
    }

    public string getString()
    {
        return str;
    }

    public override string ToString()
    {
        return str;
    }

    private JVMReference getStringReference()
    {
        if (reference != null)
        {
            return reference;
        }
        try
        {
            JVMInstance instance = new JVMInstance(JVMClassFileLoader.getClassByName("java/lang/string"), null);
            /* TODO load string or something idk */
            return instance.createReference();
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return null;
        }
    }

    public override IUnitSerializable toIUnitSerializable()
    {
        return reference;
    }
}
