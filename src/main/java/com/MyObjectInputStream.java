package com;

import javassist.*;

import java.io.*;

public class MyObjectInputStream extends ObjectInputStream{
    public MyObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    protected Class<?> resolveClass(ObjectStreamClass desc)
            throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            return Class.forName(name, false, sun.misc.VM.latestUserDefinedLoader());
        } catch (ClassNotFoundException ex) {
            try {
                return createClass(desc);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    private Class createClass(ObjectStreamClass desc) throws Exception {
        String name = desc.getName();
        ObjectStreamField[] fields = desc.getFields();
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass(name);
        CtClass interf = pool.getCtClass(Serializable.class.getName()); //获取代理对象的接口类
        CtClass[] classes = new CtClass[]{interf};
        cc.setInterfaces(classes);
        for (ObjectStreamField field : fields) {
            String name1 = field.getName();
            String name2 = field.getType().getName();
            CtField param = new CtField(pool.get(name2), name1, cc);
            param.setModifiers(Modifier.PUBLIC);
            cc.addField(param);
        }
        // 4. 添加无参的构造函数
        CtConstructor cons = new CtConstructor(new CtClass[]{}, cc);
        cons.setBody("{System.out.println(\"\");}");
        cc.addConstructor(cons);
        CtField param = new CtField(pool.get("long"), "serialVersionUID", cc);
        param.setModifiers(Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);
        cc.addField(param, CtField.Initializer.constant(desc.getSerialVersionUID()));
        //这里会将这个创建的类对象编译为.class文件/Users/fengao
        cc.writeFile("/Users/fengao/");
        return cc.toClass();
    }

}
