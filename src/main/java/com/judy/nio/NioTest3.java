package com.judy.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: judy
 * @Description: 把数据写入buffer,然后把buffer数据传递给channel,
 * @Date: Created in 17:04 2019/5/13
 */
public class NioTest3 {
    public static void main(String[] args) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest3.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(512);
        byte[] message = "judyjudyjudy".getBytes();
        for (int i = 0; i < message.length; i++) {
            allocate.put(message[i]);
        }
//       ByteBuffer put = allocate.put(message);
        allocate.flip();
        channel.write(allocate);
        fileOutputStream.close();
    }

}
