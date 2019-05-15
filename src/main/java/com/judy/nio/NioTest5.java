package com.judy.nio;

import java.nio.ByteBuffer;

/**
 * @Author: judy
 * @Description: buffer跟数据操作
 * @Date: Created in 9:43 2019/5/14
 */
public class NioTest5 {
    public static void main(String[] args){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(15);
        byteBuffer.putLong(50000000000L);
        byteBuffer.putDouble(14.2343);
        byteBuffer.putChar('你');
        byteBuffer.putShort((short) 2);
        byteBuffer.putChar('我');
        byteBuffer.flip();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getDouble());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getChar());
    }
}
