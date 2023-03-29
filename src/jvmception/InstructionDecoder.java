package jvmception;

import jvmception.instructions.Instruction;
import jvmception.instructions.InstructionALoad;
import jvmception.instructions.InstructionAStore;
import jvmception.instructions.InstructionConditional;
import jvmception.instructions.InstructionConditional2;
import jvmception.instructions.InstructionConst;
import jvmception.instructions.InstructionConvert;
import jvmception.instructions.InstructionFloatingCompare;
import jvmception.instructions.InstructionGoto;
import jvmception.instructions.InstructionLoad;
import jvmception.instructions.InstructionLoadN;
import jvmception.instructions.InstructionLookupSwitch;
import jvmception.instructions.InstructionRefConidtional;
import jvmception.instructions.InstructionReturn;
import jvmception.instructions.InstructionStore;
import jvmception.instructions.InstructionStoreN;
import jvmception.instructions.InstructionTableSwitch;
import jvmception.instructions.InstructionXYtoZ;
import jvmception.jvmtypes.IUnitSerializable;
import jvmception.jvmtypes.JVMDouble;
import jvmception.jvmtypes.JVMFloat;
import jvmception.jvmtypes.JVMInteger;
import jvmception.jvmtypes.JVMLong;
import jvmception.jvmtypes.JVMReference;
import jvmception.jvmtypes.Unit;

public class InstructionDecoder {
	private Instruction[] instructionTable;
	private JVMReference nullReference = new JVMReference();
	
	public InstructionDecoder() {
		instructionTable = new Instruction[256];
		
		/* nop */
		instructionTable[0x00] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				
			}
		};
		
		/* aconst_null */
		instructionTable[0x01] = new InstructionConst(nullReference);

		/* iconst_m1 */
		instructionTable[0x02] = new InstructionConst(new JVMInteger(-1));

		/* iconst_0 */
		instructionTable[0x03] = new InstructionConst(new JVMInteger(0));
		
		/* iconst_1 */
		instructionTable[0x04] = new InstructionConst(new JVMInteger(1));
		
		/* iconst_2 */
		instructionTable[0x05] = new InstructionConst(new JVMInteger(2));
		
		/* iconst_3 */
		instructionTable[0x06] = new InstructionConst(new JVMInteger(3));
		
		/* iconst_4 */
		instructionTable[0x07] = new InstructionConst(new JVMInteger(4));
		
		/* iconst_5 */
		instructionTable[0x08] = new InstructionConst(new JVMInteger(5));
		
		/* lconst_0 */
		instructionTable[0x09] = new InstructionConst(new JVMLong(0));
		
		/* lconst_1 */
		instructionTable[0x0a] = new InstructionConst(new JVMLong(1));
		
		/* fconst_0 */
		instructionTable[0x0b] = new InstructionConst(new JVMFloat(0.0f));
		
		/* fconst_1 */
		instructionTable[0x0c] = new InstructionConst(new JVMFloat(1.0f));
		
		/* fconst_2 */
		instructionTable[0x0d] = new InstructionConst(new JVMFloat(2.0f));
		
		/* dconst_0 */
		instructionTable[0x0e] = new InstructionConst(new JVMDouble(0.0));
		
		/* dconst_1 */
		instructionTable[0x0f] = new InstructionConst(new JVMDouble(1.0));
		
		/* bipush */
		instructionTable[0x10] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				byte b1 = frame.getNextByte();
				JVMInteger i = new JVMInteger(b1 & 0xFF);
				frame.push(i);
			}
		};
		
		/* sipush */
		instructionTable[0x11] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				byte b1 = frame.getNextByte();
				byte b2 = frame.getNextByte();
				JVMInteger i = new JVMInteger((b1 & 0xFF00) | (b2 & 0xFF));
				frame.push(i);
			}
		};
		
		/* ldc */
		instructionTable[0x12] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				int index = frame.getNextByte() & 0xFF;
				frame.push(frame.getConst(index).toIUnitSerializable());
			}
		};
		
		/* ldc_w */
		instructionTable[0x13] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				int index = frame.getNextByte() << 8 | frame.getNextByte();
				frame.push(frame.getConst(index).toIUnitSerializable());
			}
		};
		
		/* ldc2_w */
		/* Due to how the const pool is implemented, ldc_w does the same as ldc2_w 
		 * TODO: add some type sanity checks or something.*/
		instructionTable[0x14] = instructionTable[0x13];
		
		/* iload */
		instructionTable[0x15] = new InstructionLoadN(new JVMInteger());
		
		/* lload */
		instructionTable[0x16] = new InstructionLoadN(new JVMLong());
		
		/* fload */
		instructionTable[0x17] = new InstructionLoadN(new JVMFloat());
		
		/* dload */
		instructionTable[0x18] = new InstructionLoadN(new JVMDouble());
		
		/* aload */
		instructionTable[0x19] = new InstructionLoadN(new JVMReference());
		
		/* iload_0 */
		instructionTable[0x1A] = new InstructionLoad(0, new JVMInteger());
		
		/* iload_0 */
		instructionTable[0x1B] = new InstructionLoad(1, new JVMInteger());
		
		/* iload_0 */
		instructionTable[0x1C] = new InstructionLoad(2, new JVMInteger());
		
		/* iload_0 */
		instructionTable[0x1D] = new InstructionLoad(3, new JVMInteger());
		
		/* lload_0 */
		instructionTable[0x1E] = new InstructionLoad(0, new JVMLong());
		
		/* lload_1 */
		instructionTable[0x1F] = new InstructionLoad(1, new JVMLong());
		
		/* lload_2 */
		instructionTable[0x20] = new InstructionLoad(2, new JVMLong());
		
		/* lload_3 */
		instructionTable[0x21] = new InstructionLoad(3, new JVMLong());
		
		/* fload_0 */
		instructionTable[0x22] = new InstructionLoad(0, new JVMFloat());
		
		/* fload_1 */
		instructionTable[0x23] = new InstructionLoad(1, new JVMFloat());
		
		/* fload_2 */
		instructionTable[0x24] = new InstructionLoad(2, new JVMFloat());
		
		/* fload_3 */
		instructionTable[0x25] = new InstructionLoad(3, new JVMFloat());
		
		/* dload_0 */
		instructionTable[0x26] = new InstructionLoad(0, new JVMDouble());
		
		/* dload_1 */
		instructionTable[0x27] = new InstructionLoad(1, new JVMDouble());
		
		/* dload_2 */
		instructionTable[0x28] = new InstructionLoad(2, new JVMDouble());
		
		/* dload_3 */
		instructionTable[0x29] = new InstructionLoad(3, new JVMDouble());
		
		/* aload_0 */
		instructionTable[0x2A] = new InstructionLoad(0, new JVMReference());
		
		/* aload_1 */
		instructionTable[0x2B] = new InstructionLoad(1, new JVMReference());
		
		/* aload_2 */
		instructionTable[0x2C] = new InstructionLoad(2, new JVMReference());
		
		/* aload_3 */
		instructionTable[0x2D] = new InstructionLoad(3, new JVMReference());
		
		/* iaload */
		instructionTable[0x2E] = new InstructionALoad(new JVMInteger());
		
		/* laload */
		instructionTable[0x2F] = new InstructionALoad(new JVMLong());
		
		/* faload */
		instructionTable[0x30] = new InstructionALoad(new JVMFloat());
		
		/* daload */
		instructionTable[0x31] = new InstructionALoad(new JVMDouble());
		
		/* aaload */
		instructionTable[0x32] = new InstructionALoad(new JVMReference());
		
		/* TODO: This shouldn't be JVMInteger */
		/* baload */
		instructionTable[0x33] = new InstructionALoad(new JVMInteger());
		
		/* caload */
		instructionTable[0x34] = new InstructionALoad(new JVMInteger());
		
		/* saload */
		instructionTable[0x35] = new InstructionALoad(new JVMInteger());
		/* END TODO */
		
		/* istore */
		instructionTable[0x36] = new InstructionStoreN(new JVMInteger());
		
		/* lstore */
		instructionTable[0x37] = new InstructionStoreN(new JVMLong());
		
		/* fstore */
		instructionTable[0x38] = new InstructionStoreN(new JVMFloat());
		
		/* dstore */
		instructionTable[0x39] = new InstructionStoreN(new JVMDouble());
		
		/* astore */
		instructionTable[0x3A] = new InstructionStoreN(new JVMReference());
		
		/* istore_0 */
		instructionTable[0x3B] = new InstructionStore(0, new JVMInteger());
		
		/* istore_1 */
		instructionTable[0x3C] = new InstructionStore(1, new JVMInteger());
		
		/* istore_2 */
		instructionTable[0x3D] = new InstructionStore(2, new JVMInteger());
		
		/* istore_3 */
		instructionTable[0x3E] = new InstructionStore(3, new JVMInteger());
		
		/* lstore_0 */
		instructionTable[0x3F] = new InstructionStore(0, new JVMLong());
		
		/* lstore_1 */
		instructionTable[0x40] = new InstructionStore(1, new JVMLong());
		
		/* lstore_2 */
		instructionTable[0x41] = new InstructionStore(2, new JVMLong());
		
		/* lstore_3 */
		instructionTable[0x42] = new InstructionStore(3, new JVMLong());
		
		/* fstore_0 */
		instructionTable[0x43] = new InstructionStore(0, new JVMFloat());
		
		/* fstore_1 */
		instructionTable[0x44] = new InstructionStore(1, new JVMFloat());
		
		/* fstore_2 */
		instructionTable[0x45] = new InstructionStore(2, new JVMFloat());
		
		/* fstore_3 */
		instructionTable[0x46] = new InstructionStore(3, new JVMFloat());
		
		/* dstore_0 */
		instructionTable[0x47] = new InstructionStore(0, new JVMDouble());
		
		/* dstore_1 */
		instructionTable[0x48] = new InstructionStore(1, new JVMDouble());
		
		/* dstore_2 */
		instructionTable[0x49] = new InstructionStore(2, new JVMDouble());
		
		/* dstore_3 */
		instructionTable[0x4A] = new InstructionStore(3, new JVMDouble());
		
		/* astore_0 */
		instructionTable[0x4B] = new InstructionStore(0, new JVMReference());
		
		/* astore_1 */
		instructionTable[0x4C] = new InstructionStore(1, new JVMReference());
		
		/* astore_2 */
		instructionTable[0x4D] = new InstructionStore(2, new JVMReference());
		
		/* astore_3 */
		instructionTable[0x4E] = new InstructionStore(3, new JVMReference());
		
		/* iastore */
		instructionTable[0x4F] = new InstructionAStore(new JVMInteger());
		
		/* lastore */
		instructionTable[0x50] = new InstructionAStore(new JVMLong());
		
		/* fastore */
		instructionTable[0x51] = new InstructionAStore(new JVMFloat());
		
		/* dastore */
		instructionTable[0x52] = new InstructionAStore(new JVMDouble());
		
		/* aastore */
		instructionTable[0x53] = new InstructionAStore(new JVMReference());
		
		/* TODO: This shouldn't be JVMInteger */
		/* bastore */
		instructionTable[0x54] = new InstructionAStore(new JVMInteger());
		
		/* castore */
		instructionTable[0x55] = new InstructionAStore(new JVMInteger());
		
		/* sastore */
		instructionTable[0x56] = new InstructionAStore(new JVMInteger());
		/* END TODO */
		
		/* pop */
		instructionTable[0x57] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u = new Unit();
				frame.pop(u);
			}
		};
		
		/* pop2 */
		instructionTable[0x58] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u = new Unit();
				frame.pop(u);
				frame.pop(u);
			}
		};
		
		/* dup */
		instructionTable[0x59] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u = new Unit();
				frame.pop(u);
				frame.push(u);
				frame.push(u);
			}
		};
		
		/* dup_x1 */
		instructionTable[0x5A] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.push(u1);
				frame.push(u2);
				frame.push(u1);
			}
		};
		
		/* dup_x2 */
		instructionTable[0x5B] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit(), u3 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.pop(u3);
				frame.push(u1);
				frame.push(u3);
				frame.push(u2);
				frame.push(u1);
			}
		};
		
		/* dup_2 */
		instructionTable[0x5C] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.push(u2);
				frame.push(u1);
				frame.push(u2);
				frame.push(u1);
			}
		};
		
		/* dup2_x1 */
		instructionTable[0x5D] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit(), u3 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.pop(u3);
				frame.push(u2);
				frame.push(u1);
				frame.push(u3);
				frame.push(u2);
				frame.push(u1);
			}
		};
		
		/* dup2_x2 */
		instructionTable[0x5E] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit(), u3 = new Unit(), u4 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.pop(u3);
				frame.pop(u4);
				frame.push(u2);
				frame.push(u1);
				frame.push(u4);
				frame.push(u3);
				frame.push(u2);
				frame.push(u1);
			}
		};
		
		/* swap */
		instructionTable[0x5F] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				Unit u1 = new Unit(), u2 = new Unit();
				frame.pop(u1);
				frame.pop(u2);
				frame.push(u1);
				frame.push(u2);
			}
		};
		
		/* iadd */
		instructionTable[0x60] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() + ((JVMInteger)p2).intValue());
			}
		};
		
		/* ladd */
		instructionTable[0x61] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() + ((JVMLong)p2).longValue());
			}
		};
		
		/* fadd */
		instructionTable[0x62] = new InstructionXYtoZ(new JVMFloat(), new JVMFloat()) {
			
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMFloat(((JVMFloat)p1).floatValue() + ((JVMFloat)p2).floatValue());
			}
		};
		
		/* dadd */
		instructionTable[0x63] = new InstructionXYtoZ(new JVMDouble(), new JVMDouble()) {
			
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMDouble(((JVMDouble)p1).doubleValue() + ((JVMDouble)p2).doubleValue());
			}
		};
		
		/* isub */
		instructionTable[0x64] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() - ((JVMInteger)p2).intValue());
			}
		};
		
		/* lsub */
		instructionTable[0x65] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() - ((JVMLong)p2).longValue());
			}
		};
		
		/* fsub */
		instructionTable[0x66] = new InstructionXYtoZ(new JVMFloat(), new JVMFloat()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMFloat(((JVMFloat)p1).floatValue() - ((JVMFloat)p2).floatValue());
			}
		};
		
		/* dsub */
		instructionTable[0x67] = new InstructionXYtoZ(new JVMDouble(0), new JVMDouble(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMDouble(((JVMDouble)p1).doubleValue() - ((JVMDouble)p2).doubleValue());
			}
		};
		
		/* imul */
		instructionTable[0x68] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() * ((JVMInteger)p2).intValue());
			}
		};
		
		/* lmul */
		instructionTable[0x69] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() * ((JVMLong)p2).longValue());
			}
		};
		
		/* fmul */
		instructionTable[0x6A] = new InstructionXYtoZ(new JVMFloat(0), new JVMFloat(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMFloat(((JVMFloat)p1).floatValue() * ((JVMFloat)p2).floatValue());
			}
		};
		
		/* dmul */
		instructionTable[0x6B] = new InstructionXYtoZ(new JVMDouble(0), new JVMDouble(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMDouble(((JVMDouble)p1).doubleValue() * ((JVMDouble)p2).doubleValue());
			}
		};
		
		/* idiv */
		instructionTable[0x6C] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() / ((JVMInteger)p2).intValue());
			}
		};
		
		/* ldiv */
		instructionTable[0x6D] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() / ((JVMLong)p2).longValue());
			}
		};
		
		/* fdiv */
		instructionTable[0x6E] = new InstructionXYtoZ(new JVMFloat(0), new JVMFloat(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMFloat(((JVMFloat)p1).floatValue() / ((JVMFloat)p2).floatValue());
			}
		};
		
		/* ddiv */
		instructionTable[0x6F] = new InstructionXYtoZ(new JVMDouble(0), new JVMDouble(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMDouble(((JVMDouble)p1).doubleValue() / ((JVMDouble)p2).doubleValue());
			}
		};
		
		/* irem */
		instructionTable[0x70] = new InstructionXYtoZ(new JVMInteger(0), new JVMInteger(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() % ((JVMInteger)p2).intValue());
			}
		};
		
		/* lrem */
		instructionTable[0x71] = new InstructionXYtoZ(new JVMLong(0), new JVMLong(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() % ((JVMLong)p2).longValue());
			}
		};
		
		/* frem */
		instructionTable[0x72] = new InstructionXYtoZ(new JVMFloat(0), new JVMFloat(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMFloat(((JVMFloat)p1).floatValue() % ((JVMFloat)p2).floatValue());
			}
		};
		
		/* drem */
		instructionTable[0x73] = new InstructionXYtoZ(new JVMDouble(0), new JVMDouble(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMDouble(((JVMDouble)p1).doubleValue() % ((JVMDouble)p2).doubleValue());
			}
		};
		
		/* ineg */
		instructionTable[0x74] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				JVMInteger data = new JVMInteger();
				frame.pop(data);
				data.setData(-data.intValue());
			}
		};
		
		/* lneg */
		instructionTable[0x75] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				JVMLong data = new JVMLong();
				frame.pop(data);
				data.setData(-data.longValue());
			}
		};
		
		/* fneg */
		instructionTable[0x76] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				JVMFloat data = new JVMFloat();
				frame.pop(data);
				data.setData(-data.floatValue());
			}
		};
		
		/* dneg */
		instructionTable[0x77] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				JVMDouble data = new JVMDouble();
				frame.pop(data);
				data.setData(-data.getData());
			}
		};
		
		/* ishl */
		instructionTable[0x78] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() << ((JVMInteger)p2).intValue());
			}
		};
		
		/* lshl */
		instructionTable[0x79] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() << ((JVMLong)p2).longValue());
			}
		};
		
		/* ishr */
		instructionTable[0x7A] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() >> ((JVMInteger)p2).intValue());
			}
		};
		
		/* lshr */
		instructionTable[0x7B] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() >> ((JVMLong)p2).longValue());
			}
		};
		
		/* iushr */
		instructionTable[0x7C] = new InstructionXYtoZ(new JVMInteger(0), new JVMInteger(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				/* TODO: make unsigned */
				return new JVMInteger(((JVMInteger)p1).intValue() >> ((JVMInteger)p2).intValue());
			}
		};
		
		/* lushr */
		instructionTable[0x7D] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				/* TODO: make unsigned */
				return new JVMLong(((JVMLong)p1).longValue() >> ((JVMLong)p2).longValue());
			}
		};
		
		/* iand */
		instructionTable[0x7E] = new InstructionXYtoZ(new JVMInteger(), new JVMInteger()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() & ((JVMInteger)p2).intValue());
			}
		};
		
		/* land */
		instructionTable[0x7F] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() & ((JVMLong)p2).longValue());
			}
		};
		
		/* ior */
		instructionTable[0x80] = new InstructionXYtoZ(new JVMInteger(0), new JVMInteger(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() | ((JVMInteger)p2).intValue());
			}
		};
		
		/* lor */
		instructionTable[0x81] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() | ((JVMLong)p2).longValue());
			}
		};
		
		/* ixor */
		instructionTable[0x82] = new InstructionXYtoZ(new JVMInteger(0), new JVMInteger(0)) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMInteger(((JVMInteger)p1).intValue() ^ ((JVMInteger)p2).intValue());
			}
		};
		
		/* lxor */
		instructionTable[0x83] = new InstructionXYtoZ(new JVMLong(), new JVMLong()) {
			@Override
			protected IUnitSerializable performOperation() {
				return new JVMLong(((JVMLong)p1).longValue() ^ ((JVMLong)p2).longValue());
			}
		};
		
		/* iinc */
		instructionTable[0x84] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				byte index = frame.getNextByte();
				byte c = frame.getNextByte();
				JVMInteger i = new JVMInteger();
				
				/* TODO: check if this is always an int. */
				frame.getLocal(index, i);
				i.setData(i.intValue() + c);
				frame.setLocal(index, i);
			}
		};
		
		/* i2l */
		instructionTable[0x85] = new InstructionConvert(new JVMInteger(), new JVMLong());
		
		/* i2f */
		instructionTable[0x86] = new InstructionConvert(new JVMInteger(), new JVMFloat());
		
		/* i2d */
		instructionTable[0x87] = new InstructionConvert(new JVMInteger(), new JVMDouble());
		
		/* l2i */
		instructionTable[0x88] = new InstructionConvert(new JVMLong(), new JVMInteger());
		
		/* l2f */
		instructionTable[0x89] = new InstructionConvert(new JVMLong(), new JVMFloat());
		
		/* l2d */
		instructionTable[0x8A] = new InstructionConvert(new JVMLong(), new JVMDouble());
		
		/* f2i */
		instructionTable[0x8B] = new InstructionConvert(new JVMFloat(), new JVMInteger());
		
		/* f2l */
		instructionTable[0x8C] = new InstructionConvert(new JVMFloat(), new JVMLong());
		
		/* f2d */
		instructionTable[0x8D] = new InstructionConvert(new JVMFloat(), new JVMDouble());
		
		/* d2i */
		instructionTable[0x8E] = new InstructionConvert(new JVMDouble(), new JVMInteger());
		
		/* d2l */
		instructionTable[0x8F] = new InstructionConvert(new JVMDouble(), new JVMLong());
		
		/* d2f */
		instructionTable[0x90] = new InstructionConvert(new JVMDouble(), new JVMFloat());
		
		/* i2b */
		instructionTable[0x91] = new InstructionConvert(new JVMInteger(), new JVMInteger());
		
		/* i2c */
		instructionTable[0x92] = new InstructionConvert(new JVMInteger(), new JVMInteger());
		
		/* i2s */ 
		instructionTable[0x93] = new InstructionConvert(new JVMInteger(), new JVMInteger());
		
		/* lcmp */
		instructionTable[0x94] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				JVMLong l1 = new JVMLong(), l2 = new JVMLong();
				frame.pop(l2);
				frame.pop(l1);
				JVMInteger result = new JVMInteger(0);
				if (l1.longValue() > l2.longValue()) {
					result.setData(1);
				}
				else if (l2.longValue() > l1.longValue()) {
					result.setData(-1);
				}
				frame.push(result);
			}
		};
		
		/* fcmpl */
		instructionTable[0x95] = new InstructionFloatingCompare(new JVMFloat(), new JVMFloat(), 
				false);
		
		/* fcmpg */
		instructionTable[0x96] = new InstructionFloatingCompare(new JVMFloat(), new JVMFloat(), 
				true);
		
		/* dcmpl */
		instructionTable[0x97] = new InstructionFloatingCompare(new JVMDouble(), new JVMDouble(), 
				false);
		
		/* dcmpg */
		instructionTable[0x98] = new InstructionFloatingCompare(new JVMDouble(), new JVMDouble(), 
				true);
		
		
		/* ifeq */
		instructionTable[0x99] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i == 0;
			}
		};
		
		/* ifne */
		instructionTable[0x9A] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i != 0;
			}
		};
		
		/* iflt */
		instructionTable[0x9B] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i < 0;
			}
		};
		
		/* ifge */
		instructionTable[0x9C] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i >= 0;
			}
		};
		
		/* iflt */
		instructionTable[0x9D] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i > 0;
			}
		};
		
		/* ifge */
		instructionTable[0x9E] = new InstructionConditional() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i <= 0;
			}
		};
		
		/* if_icmpeq */
		instructionTable[0x9F] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i == 0;
			}
		};
		
		/* if_icmpne */
		instructionTable[0xA0] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i != 0;
			}
		};
		
		/* if_icmplt */
		instructionTable[0xA1] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i < 0;
			}
		};
		
		/* if_icmpge */
		instructionTable[0xA2] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i >= 0;
			}
		};
		
		/* if_icmpgt */
		instructionTable[0xA3] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i > 0;
			}
		};
		
		/* if_icmple */
		instructionTable[0xA4] = new InstructionConditional2() {
			
			@Override
			protected boolean checkCondition(int i) {
				return i <= 0;
			}
		};
		
		/* if_acmpeq */
		instructionTable[0xA5] = new InstructionRefConidtional(false);
		
		/* if_acmpne */
		instructionTable[0xA6] = new InstructionRefConidtional(true);
		
		/* goto */
		instructionTable[0xA7] = new InstructionGoto();
		
		/* jsr */
		instructionTable[0xA8] = new InstructionGoto(){
			
			@Override
			public void execute(CallFrame frame) {
				JVMInteger returnAddress = new JVMInteger(frame.getCurrentIndex());
				frame.push(returnAddress);
				super.execute(frame);
			}
		};
		
		/* ret */
		instructionTable[0xA9] = new Instruction() {
			
			@Override
			public void execute(CallFrame frame) {
				byte varIdx = frame.getNextByte();
				JVMInteger returnAddress = new JVMInteger();
				frame.getLocal(varIdx, returnAddress);
				frame.setCurrentIndex(returnAddress.intValue());
			}
		};
		
		/* tableswitch */
		instructionTable[0xAA] = new InstructionTableSwitch();
		
		/* lookupswitch */
		instructionTable[0xAB] = new InstructionLookupSwitch();
		
		/* ireturn */
		instructionTable[0xAC] = new InstructionReturn(new JVMInteger());
		
		/* lreturn */
		instructionTable[0xAD] = new InstructionReturn(new JVMLong());
		
		/* freturn */
		instructionTable[0xAE] = new InstructionReturn(new JVMFloat());
		
		/* dreturn */
		instructionTable[0xAF] = new InstructionReturn(new JVMDouble());
		
		/* areturn */
		instructionTable[0xB0] = new InstructionReturn(new JVMReference());
		
		/* return  */
		instructionTable[0xB1] = new InstructionReturn(null);
	}
}
