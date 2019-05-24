package com.judy.zero.copy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 15:59 2019/5/16
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String filName = "";
        FileChannel fileChannel = new FileInputStream(filName).getChannel();
        long startTime = System.currentTimeMillis();

        //将给定的文件信息写到指定的Channel上面,把磁盘上的内容写到了socketChannel上面, 没有拷贝?
        //transferTo他就是借助底层零拷贝实现的
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送总字节数" + transferCount+",耗时"+(System.currentTimeMillis()-startTime));

        fileChannel.close();




    }
}
