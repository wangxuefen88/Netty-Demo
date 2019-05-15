package com.judy.nio;

import java.nio.ByteBuffer;

/**
 * @Author: judy
 * @Description: byteBuffer只能读,不能修改
 * @Date: Created in 10:56 2019/5/14
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.getClass());
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

    }
}
