package com.jie.bookshare.utils;


import java.io.*;

public class SerializeUtil {

    public static byte[] serializeObjToBytes(Object obj) throws IOException {
        byte[] bytes;
        /**
         * 关键步骤：将对象变成二进制数组。使用对象输出流将对象
         *          写入ByteArrayOutputStream里再使用toByteArray()变成byte数组
         */
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(obj);
        bytes = bos.toByteArray();

        return bytes;
    }

    public static Object deSerializeBytesToObj(byte[] bytes) throws Exception{
        Object obj;

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        obj = ois.readObject();

        return obj;
    }
}
