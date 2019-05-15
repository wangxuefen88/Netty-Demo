package com.judy.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: judy
 * @Description: 内存映射文件 , 位于对外内存
 * @Date: Created in 15:39 2019/5/14
 */
public class NioTest9 {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(3, (byte) 'b');
        randomAccessFile.close();
    }
}
