package com.judy.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: judy
 * @Description: 文件输入流... Channel与buffer , buffer从channel读取数据,转换为读->buffer
 * @Date: Created in 16:54 2019/5/13
 */
public class NioTest2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        //buffer读取数据,channel给予通道让buffer读,把Channel的数据读到buffer1
        channel.read(buffer);
        //写状态转变为读状态
        buffer.flip();
        while (buffer.remaining() > 0) {
            //读取(跟程序关联)
            byte b = buffer.get();
            System.out.println("Character--"+(char)b);
        }
        fileInputStream.close();
    }
}
