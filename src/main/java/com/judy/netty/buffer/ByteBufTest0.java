package com.judy.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: judy
 * @Description: netty与NIo的Bytebuffer的区别是增加了 读和写的空间
 * @Date: Created in 9:16 2019/5/23
 */
public class ByteBufTest0 {
    public static void main(String[] args){
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
