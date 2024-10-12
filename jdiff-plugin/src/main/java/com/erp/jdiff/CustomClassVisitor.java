package com.erp.jdiff;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.Objects;

public class CustomClassVisitor extends ClassVisitor {

    // 格式：java/lang/Object
    private String className;
    // 是否是 DiffObject 类的子类
    private boolean isSubClassDiffObject;

    public CustomClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM9, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.isSubClassDiffObject = Objects.equals(superName, "com/erp/gameserver/model/DiffEntity");
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        System.out.println(" 1 name " + name + "descriptor " + descriptor);
        if (isSubClassDiffObject()) {
            System.out.println(" 2 name " + name + "descriptor " + descriptor);
            if (isGetterMethod(name, descriptor)) {
                System.out.println(" 3 name " + name + "descriptor " + descriptor);
                return new GetterMethodVisitor(mv, className, name, descriptor);
            } else if (isSetterMethod(name)) {
                System.out.println(" 4 name " + name + "descriptor" + descriptor);
                return new SetterMethodVisitor(mv, className, name, descriptor);
            }
        }

        System.out.println(" 5 name " + name + "descriptor " + descriptor);
        return mv;
    }

    public boolean isSubClassDiffObject() {
        return isSubClassDiffObject;
    }

    // 判断是否为 getter 方法
    private static boolean isGetterMethod(String methodName, String methodDesc) {
        return (methodName.startsWith("get") || methodName.startsWith("is")) && methodDesc.startsWith("()") && !methodName.equals("getClass");
    }

    // 判断是否为 setter 方法
    private static boolean isSetterMethod(String methodName) {
        return methodName.startsWith("set");
    }

    static class SetterMethodVisitor extends MethodVisitor {

        private final String className;
        // 方法名
        private final String methodName;
        // 方法描述符
        private final String methodDescriptor;

        public SetterMethodVisitor(MethodVisitor mv, String className, String methodName, String methodDescriptor) {
            super(Opcodes.ASM9, mv);
            this.className = className;
            this.methodName = methodName;
            this.methodDescriptor = methodDescriptor;
        }

        @Override
        public void visitCode() {
            // this.fieldChanged("rid", rid, this.rid);
            String fieldName = extractFieldName(methodName);
            String fieldDescriptor = extractFieldDescriptor(methodDescriptor);
            mv.visitVarInsn(Opcodes.ALOAD, 0); // 将 this 加载到操作数栈
            mv.visitLdcInsn(fieldName); // 加载字段名到操作数栈
            mv.visitVarInsn(Opcodes.ALOAD, 1); // 将方法的第一个参数加载到操作数栈
            mv.visitVarInsn(Opcodes.ALOAD, 0); // 将 this 加载到操作数栈
            mv.visitFieldInsn(Opcodes.GETFIELD, className, fieldName, fieldDescriptor); // 加载字段值到操作数栈
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, "fieldChanged",
                    "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", false);
        }

        // 提取字段名
        private String extractFieldName(String methodName) {
            return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
        }

        // 提取字段描述符 (Ljava/lang/String;)V >> Ljava/lang/String
        private static String extractFieldDescriptor(String methodDescriptor) {
            return Type.getArgumentTypes(methodDescriptor)[0].getDescriptor();
        }
    }

    static class GetterMethodVisitor extends MethodVisitor {

        private final String className;
        // 方法名
        private final String methodName;
        // 方法描述符
        private final String methodDescriptor;

        public GetterMethodVisitor(MethodVisitor mv, String className, String methodName, String methodDescriptor) {
            super(Opcodes.ASM9, mv);
            this.className = className;
            this.methodName = methodName;
            this.methodDescriptor = methodDescriptor;
        }

        @Override
        public void visitCode() {
            mv.visitCode();
            // 插入调用 this.updatePath(fieldName, fieldValue); 的指令
            String fieldName = extractFieldName(methodName);
            String fieldDescriptor = extractFieldDescriptor(methodDescriptor);

            mv.visitVarInsn(Opcodes.ALOAD, 0); // 将 this 加载到操作数栈
            mv.visitLdcInsn(fieldName); // 加载字段名到操作数栈
            mv.visitVarInsn(Opcodes.ALOAD, 0); // 将 this 加载到操作数栈
            mv.visitFieldInsn(Opcodes.GETFIELD, className, fieldName, fieldDescriptor); // 加载字段值到操作数栈
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, "updatePath", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
        }

        // 提取字段名
        private String extractFieldName(String methodName) {
            return methodName.startsWith("get") ? methodName.substring(3, 4).toLowerCase() + methodName.substring(4) :
                    methodName.startsWith("is") ? methodName.substring(2, 3).toLowerCase() + methodName.substring(3) : "";
        }

        // 提取字段描述符
        private static String extractFieldDescriptor(String methodDescriptor) {
            return methodDescriptor.substring(2);
        }
    }
}
