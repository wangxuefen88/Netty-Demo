package com.judy.netty.fourthexample.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * @Author: judy
 * @Description:
 * @Date: Created in 16:08 2019/5/11
 */
public class MyFourthChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //如果在某段指定时间间断没段则执行这段代码
        //空闲状态监测处理器
        pipeline.addLast(new IdleStateHandler(5, 7, 3, TimeUnit.SECONDS));
        pipeline.addLast(new MyFourthChatServerHandler());

    }
}
