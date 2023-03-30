package jvmception.objects;

public abstract class JVMMember {
	private Visibility visibility;
	private String name;
	
	public JVMMember(String name, Visibility visibility) {
		this.name = name;
		this.visibility = visibility;
	}
	
	public String getMemberName() {
		return name;
	}
	
	public Visibility getVisibility() {
		return visibility;
	}
}
