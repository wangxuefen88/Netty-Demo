package com.judy.netty.fourthexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: judy
 * @Description: 服务端
 * @Date: Created in 16:05 2019/5/11
 */
public class MyFourthChatServer {
    public static void main(String[] args) {
        EventLoopGroup BossEventLoop = new NioEventLoopGroup();
        EventLoopGroup WorkEventLoop = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(BossEventLoop, WorkEventLoop).
                    channel(NioServerSocketChannel.class).
                    handler(new LoggingHandler(LogLevel.INFO)).
                    childHandler(new MyFourthChatServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8889).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            BossEventLoop.shutdownGracefully();
            WorkEventLoop.shutdownGracefully();
        }
    }
}

