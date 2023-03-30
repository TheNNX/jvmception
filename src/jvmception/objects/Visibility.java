package jvmception.objects;

public enum Visibility {
	PUBLIC,
	PACKAGE,
	PROTECTED,
	PRIVATE;
	
	public static Visibility fromAccessMask(int access) {
		if ((access & 1) != 0)
			return Visibility.PUBLIC;
		if ((access & 2) != 0)
			return Visibility.PRIVATE;
		if ((access & 4) != 0)
			return Visibility.PROTECTED;
		return Visibility.PACKAGE;
	}
}
