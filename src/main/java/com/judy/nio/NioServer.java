package com.judy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: judy
 * @Description: nio实现服务端,实现一个服务端接受多个客户端并且分发消息
 * @Date: Created in 11:13 2019/5/15
 */
public class NioServer {
    //维护客户端连接信息
    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        /**
         * 模板1必不可少,准备工作
         */
        //创建一个ServerSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //配置为非阻塞
        serverSocketChannel.configureBlocking(false);
//        serverSocketChannel获取服务器端Socket对象
        ServerSocket serverSocket = serverSocketChannel.socket();
        //ip地址绑定,表示服务器端监听那个端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        serverSocket.bind(inetSocketAddress);

        /***
         * 创建Selector对象
         */
        //创建Selector对象(在服务器端创建选择器对象)
        Selector selector = Selector.open();
        //把serverSocketChannel对象注册到Selector选择器上,SelectionKey.OP_ACCEPT:表示服务器端会关注连接这个事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        /**
         * 事件处理(死循环)
         */
        while (true) {
            //进行阻塞,一直等到有事件发生则不阻塞,返回的是他所关注的事件数量
            int selectNumber = selector.select();
            System.out.println("连接数:" + selectNumber);
            //获取selectionKey所关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            /**
             * 针对不同的事件做相应的处理
             */
            selectionKeys.forEach(selectionKey -> {
                //表示对应客户端的Socket对象
                final SocketChannel client;
                //客户端向服务端发起连接
                if (selectionKey.isAcceptable()) {

                    try {
                        //获取当前事件在那个通道上面
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        //服务端需要返回给客户端一个serverSocket对象(表示客户端已经跟服务端建立好了连接)-->所以之后的操作都是针对的SocketChannel对象(serverSocketChannel对象现在的任务已经完成了,用不上了(就是起到建立连接))
                        client = server.accept();
                        /**
                         * 把SocketChannel也注册到Selector上面
                         */
                        //配置为非阻塞的
                        client.configureBlocking(false);
                        //
                        /**
                         * 目前Selector上面注册了两个Channel对象,一个是ServerSocketChannel,另一个是SocketChannel,他们两个的作用是不一样的
                         * ServerSocketChannel关注的事件是连接,SocketChannel关注的事件是客户端和服务器端读取事件
                         */
                        client.register(selector, SelectionKey.OP_READ);
                        /**
                         * 把客户端的信息记录到服务器端上面,这样服务器才可以实现消息的分发
                         */
                        //key ,client为value
                        String key = "[" + UUID.randomUUID().toString() + "]";
                        clientMap.put(key, client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) { //是否是读取事件
                    client = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    try {
                        //把客户端的发送的数据读到buffer中
                        int readCount = client.read(readBuffer);
                        //如果readCount>0则说明客户端已经写入数据
                        if (readCount > 0) {
                            //把数据返回给客户端
                            readBuffer.flip();
                            Charset charset = Charset.forName("utf-8");
                            //解码操作为String类型
                            String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                            System.out.println(client + ":" + receivedMessage);

                            /**
                             * 分发给所有客户端信息
                             */
                            String senderKey = null;
                            for (Map.Entry<String, SocketChannel> socketChannelEntry : clientMap.entrySet()) {
                                if (client == socketChannelEntry.getValue()) {
                                    senderKey = socketChannelEntry.getKey();
                                    break;
                                }
                            }

                            for (Map.Entry<String, SocketChannel> channelEntry : clientMap.entrySet()) {
                                SocketChannel value = channelEntry.getValue();
                                ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                writeBuffer.put((senderKey + ":" + receivedMessage).getBytes());
                                writeBuffer.flip();
                                value.write(writeBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //当处理完selectionKey的时候一定要从集合selectionKeys删除掉,因为这代表这次的触发实践已经完事了.否则信息会被重复处理
            selectionKeys.clear();
        }
    }
}
