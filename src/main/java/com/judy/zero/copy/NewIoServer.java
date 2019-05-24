package com.judy.zero.copy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 15:34 2019/5/16
 */
public class NewIoServer {
    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        /**
         * 这句话表示的意思如果当前的端口被占用了或者是超时那么这个时候通过设置ReuseAddress我们已经可以让他连接
         */
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);


        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        while (true) {
            //这里返回的SocketChannel是阻塞模式的
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(true);

            int readCount = 0;

            while (-1 != readCount) {
                readCount = socketChannel.read(byteBuffer);
                //重新開始
                byteBuffer.rewind();
            }
        }
    }
}
