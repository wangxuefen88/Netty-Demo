package com.judy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 17:35 2019/5/14
 */
public class NIoTest12 {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[5];
        ports[0] = 5000;
        ports[1] = 5001;
        ports[2] = 5002;
        ports[3] = 5003;
        ports[4] = 5004;

        /**
         * 等待客户端建立连接
         */
        //创建selector
        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            //是针对面向流的serverSocket.调用open方法打开,但是必须调用地址方法,不能直接使用accept()方法
            /**
             * 绑定
             */
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            /**
             * 通道和选择器之间的关系
             */
            //表示selector与channel进行连接,一个selector对应多个Channel
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口" + ports[i]);
        }
        /**
         *  与客户端通信
         */
        while (true) {
            //获取所有连接,select是阻塞的
            int select = selector.select();
            System.out.println("select-:" + select);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys" + selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //是否有数据进来
                if (selectionKey.isAcceptable()) {
                    //从Select 上获取Channel
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //服务器通道的选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    iterator.remove();
                    System.out.println("获得客户端连接" + socketChannel);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
                    int bytesRead = 0;
                    while (true) {
                        /**
                         * buffer与channel的一对一关系
                         */
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();
                        int read = socketChannel.read(byteBuffer);
                        if (read<=0){
                            break;
                        }
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        bytesRead += read;
                    }
                    System.out.println("读取:"+bytesRead+",来自于"+socketChannel);
                    //当前事件已经用完所以删除
                    iterator.remove();
                }
            }
        }
    }

}
