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
         * 不停的进行循环
         */
        //接受请求
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //做具体的业务逻辑处理
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //一个用于连接,一个用于做处理,childHandler表示自己要做的业务处理部分
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());

            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


}
