package jvmception.objects;

public class JVMInterface extends JVMMember{
	private JVMInterface[] baseInterfaces;
	private JVMMethod[] methods;
	
	public JVMInterface(String name, JVMInterface[] baseInterfaces, Visibility visibility) {
		super(name, visibility);
		this.baseInterfaces = baseInterfaces.clone();
	}
	
	public JVMInterface[] getInterfaces() {
		return this.baseInterfaces;
	}
	
	public JVMMethod[] getMethods() {
		return this.methods;
	}
	
	public boolean setMethods(JVMMethod[] methods) {
		if (this.methods != null)
			return false;
		this.methods = methods;
		return true;
	}
}
