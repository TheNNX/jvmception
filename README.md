# _Java Virtual Machine_ implementation in Java and C# for the "Szybkość programów w Javie" assignment.

This is an implementation of most of the JVM instruction set. The `main` branch contains a virtual machine written in Java, the branch `csharp` contains the same program reimplemented in C#. 

## Code structure
- Instructions are implemented as subclasses of the abstract class `jvmception.instructions.Instruction` and decoded in `jvmception.InstructionDecoder`. 
- Class loading and the main method are implemented `jvmception.objects.cp.JVMClassLoader`.
- Stack management and method invocation are implemented in `jvmeption.CallFrame`.
- Heap allocations and garbage collection are implemented in `jvmeption.objects.JVMInstance` and `jvmeption.jvmtypes.JVMReference` (this functionality is rather untested though).

## Execution times
Tested with the following program...
```java
package test;

public class Test {

	public static int sField = 0;
	
	public static float testStaticMethod(int r) {
		return r*35 + 53.4f;
	}
	
	public static void main(String[] args) 
	{
		float t = 0.23f;
		
		for (int i = 0; i < 1000000; i++) {
			t += testStaticMethod(i);
			sField = (int)t + i;
		}
	}
}
```
...execution times are as follows:
- Java 17.0.4.1 (-Xmixed): 823ms
- C# 7.3: 1289ms
- Java 17.0.4.1 (-Xcomp): 1304ms
- Java 17.0.4.1 (-Xint): 27086ms

## Unimplemented instructions: 
 - invokedynamic
 - newarray
 - anewarray
 - arraylength
 - athrow
 - checkcast
 - instanceof
 - monitorenter
 - monitorexit
 - wide
 - multianewarray
 - ifnull
 - ifnonnull
 - goto_w
 - jsr_w
 
## Notable missing features:
 - Exceptions
 - Arrays
 - Runtime type and access checking 
 - Native functions
 
The implementation is otherwise more or less complete and is able to execute simple Java programs. Due to native functions being unimplemented, no standard library can be loaded. The `java.lang.Object` is built into the class loader. 
