
public class JVMInterface : JVMMember
{
    private JVMInterface[] baseInterfaces;
    private JVMMethod[] methods;

    public JVMInterface(string name, JVMInterface[] baseInterfaces, Visibility visibility) : base(name, visibility)
    {

        this.baseInterfaces = (JVMInterface[])baseInterfaces.Clone();
    }

    public JVMInterface[] getInterfaces()
    {
        return this.baseInterfaces;
    }

    public JVMMethod[] getMethods()
    {
        return this.methods;
    }

    public bool setMethods(JVMMethod[] methods)
    {
        if (this.methods != null)
            return false;
        this.methods = methods;
        return true;
    }
}
