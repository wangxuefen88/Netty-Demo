package com.judy.netty.firstexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 6:51 2019/5/5
 */
public class TestServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 不停的进行循环 (事件循环组)
         */
        //接受客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //做具体的业务逻辑处理,响应处理
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            /**
             * 服务端配置类
             */
            //减少netty的创建工作的一个对象,
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //一个用于连接,一个用于做处理,childHandler表示自己要做的业务处理部分
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());
            //sync阻塞同步
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


}
