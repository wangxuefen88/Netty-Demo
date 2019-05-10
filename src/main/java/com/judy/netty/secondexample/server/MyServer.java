package com.judy.netty.secondexample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: judy
 * @Description: 服务端
 * @Date: Created in 21:30 2019/5/9
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup BossEventLoop = new NioEventLoopGroup();
        EventLoopGroup WorkEventLoop = new NioEventLoopGroup();
        /**
         * Handler表示的是BossEventLoop.接受连接
         * childHandler 表示的是workEventLoop,处理连接之后的操作
         */
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(BossEventLoop, WorkEventLoop).channel(NioServerSocketChannel.class).childHandler( new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
//            channelFuture.channel().close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            BossEventLoop.shutdownGracefully();
            WorkEventLoop.shutdownGracefully();
        }
    }
}
