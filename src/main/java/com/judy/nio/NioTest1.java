package com.judy.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @Author: judy
 * @Description: 单独讲解 Buffer
 * @Date: Created in 16:12 2019/5/13
 */
public class NioTest1 {
    public static void main(String[] args){
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("Capacity--"+ buffer.capacity());
        for (int i = 0; i < buffer.capacity() ; i++) {
            //写到Buffer
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }
        //buffer中写与读的状态转换
        buffer.flip();
        //buffer读
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
