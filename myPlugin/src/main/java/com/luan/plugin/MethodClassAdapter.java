package com.luan.plugin;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;

public class MethodClassAdapter extends ClassVisitor implements Opcodes {
    private String className;

    public MethodClassAdapter(ClassVisitor classVisitor) {
        super(ASM7, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if ("android/support/v4/app/FragmentActivity".equals(className)) {
            if ("onCreate".equals(name)) {
                return (mv == null) ? null : new OnCreateMethodVisitor(mv);
            }
        }
       return mv;
    }
}
