package jvmception.objects.cp;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodInfo extends FieldInfo{
	
	private CodeAttribute code = null;
	
	public MethodInfo(DataInputStream dis, CpInfo[] cpInfo) throws IOException {
		super(dis, cpInfo);
		
		for (int i = 0; i < this.getAttributes().length; i++) {
			if (this.getAttributes()[i] instanceof CodeAttribute){
				code = (CodeAttribute) this.getAttributes()[i];
			}
		}
	}

	public CodeAttribute getCodeAttribute() {
		return code;
	}
}
