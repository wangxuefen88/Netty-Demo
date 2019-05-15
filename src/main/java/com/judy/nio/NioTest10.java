package com.judy.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Author: judy
 * @Description: 文件锁,共享锁和排他锁
 * @Date: Created in 15:46 2019/5/14
 */
public class NioTest10 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //获取文件锁,位置,锁多长,共享还是排他.true共享
        FileLock lock = channel.lock(3, 6, true);
        //判断有效性
        System.out.println("valid:"+lock.isValid());
        //是否共享锁
        System.out.println("lock type"+lock.isShared());
        lock.release();
        randomAccessFile.close();
    }
}
