package com.luan.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM7;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.POP;

public class OnCreateMethodVisitor extends MethodVisitor {
    public OnCreateMethodVisitor( MethodVisitor classVisitor) {
        super(ASM7, classVisitor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        System.out.println("== TestMethodVisitor, owner = " + owner + ", name = " + name);
        //方法执行之前打印
        mv.visitLdcInsn("lucas");
        mv.visitLdcInsn(" [ASM 测试] method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        //方法执行之后打印
        mv.visitLdcInsn("lucas");
        mv.visitLdcInsn(" method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(POP);
    }
}
