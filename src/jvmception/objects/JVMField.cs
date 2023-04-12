
using System.Collections.Generic;

public class JVMField : JVMMember
{

    private string descriptor;
    private Unit[] staticStorage = null;
    private static List<JVMField> staticFields = new List<JVMField>();
    private JVMInterface owner;

    public JVMField(FieldInfo fieldInfo, JVMInterface owner)
        : base(fieldInfo.getName(), VisibilityHelper.GetVisibility(fieldInfo.getAccessFlags()))
    {

        descriptor = fieldInfo.getDescriptor();

        if ((fieldInfo.getAccessFlags() & 0x0008) != 0)
        {
            staticStorage = getContainedType().serialize();
            staticFields.Add(this);
        }

        this.owner = owner;
    }

    public static JVMField[] getStaticFields()
    {
        JVMField[] result = new JVMField[staticFields.Count];
        for (int i = 0; i < result.Length; i++)
            result[i] = staticFields[i];
        return result;
    }

    public string getDescriptor()
    {
        return descriptor;
    }

    public IUnitSerializable getContainedType()
    {
        return Unit.getTypeFromCode(descriptor[0]);
    }

    public void set(JVMInstance instance, IUnitSerializable data)
    {
        Unit[] dst;

        if (instance == null)
        {
            dst = this.staticStorage;
        }
        else
        {
            dst = instance.getFieldStorage(this);
        }

        Unit[] copy = data.serialize();
        for (int i = 0; i < dst.Length; i++)
        {
            dst[i] = copy[i];
        }
    }

    public IUnitSerializable get(JVMInstance instance)
    {
        Unit[] src;

        if (instance == null)
        {
            src = this.staticStorage;
        }
        else
        {
            src = instance.getFieldStorage(this);
        }

        IUnitSerializable result = this.getContainedType();
        result.deserialize(src);
        return result;
    }

    public bool isStatic()
    {
        return this.staticStorage != null;
    }

    public static JVMField getFieldFromCpIndex(CallFrame frame, int index)
    {
        CpFieldRef cpField = (CpFieldRef)frame.getConst(index);
        int nameAndTypeIndex = cpField.getNameAndTypeIndex();
        int classIndex = cpField.getClassIndex();

        CpClass cpClass = (CpClass)frame.getConst(classIndex);
        CpNameAndIndex nameAndType = (CpNameAndIndex)frame.getConst(nameAndTypeIndex);
        CpUtf8 className = (CpUtf8)frame.getConst(cpClass.getNameIndex());
        CpUtf8 fieldName = (CpUtf8)frame.getConst(nameAndType.getNameIndex());
        CpUtf8 descriptor = (CpUtf8)frame.getConst(nameAndType.getDescriptorIndex());

        JVMClass theClass = JVMClassFileLoader.getClassByName(className.getString());
        JVMField field = theClass.getField(fieldName.getString(), descriptor.getString());
        return field;
    }

    public JVMInterface getOwner()
    {
        return owner;
    }
}
