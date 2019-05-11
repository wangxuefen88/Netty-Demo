package com.judy.netty.thirdexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: judy
 * @Description: 服务端
 * @Date: Created in 10:59 2019/5/11
 */
public class MyChatServer {
    public static void main(String[] args) {
        EventLoopGroup BossEventLoop = new NioEventLoopGroup();
        EventLoopGroup WorkEventLoop = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(BossEventLoop, WorkEventLoop).channel(NioServerSocketChannel.class).childHandler(new MyChatServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            BossEventLoop.shutdownGracefully();
            WorkEventLoop.shutdownGracefully();
        }
    }
}
