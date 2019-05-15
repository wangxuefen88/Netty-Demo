package com.judy.nio;

import java.nio.ByteBuffer;

/**
 * @Author: judy
 * @Description: 分割和分片buffer , slice 和 buffer
 * @Date: Created in 10:21 2019/5/14
 */
public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.position();
        byteBuffer.limit(6);
        //返回一个新的ByteBuffer,其内容是以前buffer的一个共享子序列,新buffer的其实位置,也就是他的当前位置,两个buffer的limit,position,mark都是独立的,但是内容是一样的,如果一方修改则内容都改变
        ByteBuffer bufferNew = byteBuffer.slice();
        for (int i = 0; i < bufferNew.capacity(); i++) {
            byte b = bufferNew.get();
            b *= 2;
            bufferNew.put(i, b);
        }
        byteBuffer.position(0);
        byteBuffer.limit(byteBuffer.capacity());
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

    }
}
