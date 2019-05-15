package com.judy.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 11:34 2019/5/14
 */
public class NioTest8 {
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel channelInput = inputStream.getChannel();
        FileChannel channel1Out = outputStream.getChannel();

        ByteBuffer buffer  = ByteBuffer.allocateDirect(512);
        while (true){
            buffer.clear();
            int read = channelInput.read(buffer);
            System.out.println("read:"+read);
            if (-1 == read) {
                break;
            }
            buffer.flip();
            channel1Out.write(buffer);
        }
        channelInput.close();
        channel1Out.close();
    }
}
