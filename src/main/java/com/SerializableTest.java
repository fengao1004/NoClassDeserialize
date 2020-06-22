package com;

import com.google.gson.Gson;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws Exception {
//        serializeFlyPig();
        deserializeFlyPig();
    }

    /**
     * 序列化
     */
    private static void serializeFlyPig() throws IOException {
//        Bean beanDemo = new Bean();
//        beanDemo.i = 123;
//        beanDemo.str = "1231";
//        beanDemo.setC('c');
//        beanDemo.f = 53.65f;
//        // ObjectOutputStream 对象输出流，将 flyPig 对象存储到E盘的 flyPig.txt 文件中，完成对 flyPig 对象的序列化操作
//        File file = new File("/Users/fengao/flyPig.txt");
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
//        oos.writeObject(beanDemo);
//        System.out.println("com.FlyPig 对象序列化成功！");
//        oos.close();
    }

    /**
     * 反序列化
     */
    private static Object deserializeFlyPig() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/fengao/flyPig.txt"));
        ObjectInputStream ois = new MyObjectInputStream(fileInputStream);
        Object person = ois.readObject();
        System.out.println("com.FlyPig 对象反序列化成功！");
        System.out.println(new Gson().toJson(person));
        return person;
    }
}