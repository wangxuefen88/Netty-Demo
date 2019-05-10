package com.judy.netty.fristexample.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**

 * @Author: judy
 * @Description: 客户端和服务端一旦创建连接之后就会创建这个对象调用initChannel
 * @Date: Created in 23:26 2019/5/8
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //每一个pipeline里面都可以有多个childHandler
          ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
        channelPipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
