package com.jie.bookshare.utils;


import java.io.*;

public class SerializeUtil {

    /**
     * 序列化对象为字节数组
     * @param obj 对象
     * @return 字节数组
     */
    public static byte[] serializeObjToBytes(Object obj) throws IOException {
        byte[] bytes;
        // 关键步骤：将对象变成二进制数组。使用对象输出流将对象
        // 写入ByteArrayOutputStream里再使用toByteArray()变成byte数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        bytes = bos.toByteArray();

        return bytes;
    }

    /**
     * 反序列化字节数组为对象
     * @param bytes 字节数组
     * @return 对象
     */
    public static Object deSerializeBytesToObj(byte[] bytes) throws Exception {
        Object obj;

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        obj = ois.readObject();

        return obj;
    }
}
