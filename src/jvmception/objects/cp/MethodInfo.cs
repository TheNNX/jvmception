using System.IO;

public class MethodInfo : FieldInfo
{

    private CodeAttribute code = null;

    public MethodInfo(BinaryReaderBigEndian dis, CpInfo[] cpInfo) : base(dis, cpInfo)
    {
        for (int i = 0; i < this.getAttributes().Length; i++)
        {
            if (this.getAttributes()[i] is CodeAttribute)
            {
                code = (CodeAttribute)this.getAttributes()[i];
            }
        }
    }

    internal MethodInfo(string name, string descriptor) : base(name, descriptor)
    {

        code = new CodeAttribute();
    }

    public CodeAttribute getCodeAttribute()
    {
        return code;
    }
}
