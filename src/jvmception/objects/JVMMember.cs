
public abstract class JVMMember
{
    private Visibility visibility;
    private string name;

    public JVMMember(string name, Visibility visibility)
    {
        this.name = name;
        this.visibility = visibility;
    }

    public string getMemberName()
    {
        return name;
    }

    public Visibility getVisibility()
    {
        return visibility;
    }
}
