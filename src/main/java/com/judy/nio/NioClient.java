package com.judy.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: judy
 * @Description: 客户端向服务端发送数据
 * @Date: Created in 14:28 2019/5/15
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        //服务端针对永远都是Connect,而客户端针对是bind和access
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 8888);
        socketChannel.connect(inetSocketAddress);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            int select = selector.select();
            System.out.println("连接数client:" + select);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey selectionKey : selectionKeys) {
                //表示已经跟服务器端建立好了连接
                if (selectionKey.isConnectable()) {
                    System.out.println("client 客户端已经跟服务器端建立好了连接");
                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();


                    /***
                     * 1 跟服务端建立好真正的连接
                     * 2 起一个新的线程,读取表标准输入内容,获取用户的输入数据,通过Bytebuffer写会给服务器端
                     *
                     */

                    //判断连接是否是正在进行的状态
                    if (clientChannel.isConnectionPending()) {
                        //完成连接,表示现在连接已经正真的已经建立好了
                        clientChannel.finishConnect();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
                        byteBuffer.flip();
                        clientChannel.write(byteBuffer);
                        /**
                         * 由于客户端的从键盘输入是单线程的,所以必须另起一个线程负责客户端的键盘输入
                         */
                        ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                        executorService.submit(() -> {
                            {
                                while (true) {
                                    byteBuffer.clear();
                                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    String sendMessage = bufferedReader.readLine();
                                    byteBuffer.put(sendMessage.getBytes());
                                    byteBuffer.flip();
                                    clientChannel.write(byteBuffer);
                                }
                            }
                        });
                    }
                    clientChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int read = client.read(byteBuffer);
                    if (read > 0) {
                        String receiveMessage = new String(byteBuffer.array(), 0, read);
                        System.out.println(receiveMessage);
                    }
                }
            }
            selectionKeys.clear();
        }

    }
}
