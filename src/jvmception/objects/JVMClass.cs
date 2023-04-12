
using System;

public class JVMClass : JVMInterface
{
    private JVMClass baseClass;
    private JVMField[] fields = null;
    private IJVMConstPoolType[] constPool;

    public JVMClass(string name, JVMClass baseClass, JVMInterface[] interfaces, Visibility visibility,
                    IJVMConstPoolType[] constPool)
        : base(name, interfaces, visibility)
    {

        this.baseClass = baseClass;
        this.constPool = constPool;
    }

    public JVMField[] getFields()
    {
        return this.fields;
    }

    public bool setFields(JVMField[] fields)
    {
        if (this.fields != null)
            return false;
        this.fields = fields;
        return true;
    }

    public JVMMethod getMethod(string name, string descriptor)
    {
        foreach (JVMMethod m in this.getMethods())
        {
            if (m.getMemberName() == (name) && m.getDescriptor() == (descriptor))
            {
                return m;
            }
        }
        if (baseClass != null)
        {
            JVMMethod method = baseClass.getMethod(name, descriptor);
            if (method != null)
                return method;
        }
        return null;
    }

    public JVMField getField(string name, string descriptor)
    {
        foreach (JVMField f in this.getFields())
        {
            if (f.getMemberName() == name && f.getDescriptor() == descriptor)
            {
                return f;
            }
        }
        if (baseClass != null)
        {
            JVMField field = baseClass.getField(name, descriptor);
            if (field != null)
                return field;
        }
        return null;
    }

    public IJVMConstPoolType[] getConstPool()
    {
        return constPool;
    }

    public static JVMClass getClassFromCpIndex(CallFrame frame, int index)
    {
        CpClass cpClass = (CpClass)frame.getConst(index);
        CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
        JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
        return theClass;
    }
}
