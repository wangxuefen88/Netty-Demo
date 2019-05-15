package com.judy.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @Author: judy
 * @Description: 测试limit,position,capacity属性的改变
 * @Date: Created in 16:12 2019/5/13
 */
public class NioTest4 {
    public static void main(String[] args){
        IntBuffer buffer = IntBuffer.allocate(10);
        /**
         * 最开始创建buffer的时候,limit和Capacity都会指向最后的元素,也就是10
         */
        System.out.println("Capacity--"+ buffer.capacity());
        System.out.println("limit1->"+buffer.limit());


        for (int i = 0; i < 5 ; i++) {
            //写到Buffer
            int randomNumber = new SecureRandom().nextInt(20);
            buffer.put(randomNumber);
        }

        System.out.println("limit2->"+buffer.limit());
        //buffer中写与读的状态转换
        buffer.flip();

        //反转之后limit值改变,从10变成5
        System.out.println("limit3->"+buffer.limit());
        System.out.println("position->"+buffer.position());

        //buffer读
        while (buffer.hasRemaining()) {
            System.out.println("position->"+buffer.position());
            System.out.println("limit4->"+buffer.limit());
            System.out.println("capacity->"+buffer.capacity());
            System.out.println(buffer.get());
        }
    }
}
