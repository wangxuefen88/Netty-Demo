package com.judy.nio;

import com.google.protobuf.MessageLite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author: judy
 * @Description: buffer, scattering(散开) 与 Gathering(收集)
 * @Date: Created in 15:54 2019/5/14
 */
public class NioTest11 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(inetSocketAddress);

        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        //除非有新的连接,负责就阻塞
        /**  针对于accept()
         *
         *   do {
               var3 = this.accept0(this.fd, var4, var5);
             } while(var3 == -3 && this.isOpen());
         */
        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int bytesRead = 0;

            while (bytesRead < messageLength) {
                long read = socketChannel.read(buffers);
                bytesRead += read;
                System.out.println("bytesRead" + bytesRead);
                Arrays.asList(buffers).stream().map(buffer -> "position:" + buffer.position() + ",limit:" + buffer.limit())
                        .forEach(System.out::println);
            }
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.flip();
            });
            long bytesWritten = 0;
            while (bytesWritten < messageLength){
                long write = socketChannel.write(buffers);
                bytesWritten += write;
            }
            Arrays.asList(buffers).forEach(buffer->{
                buffer.clear();
            });
        }
    }
}























