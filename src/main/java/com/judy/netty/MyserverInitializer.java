package com.judy.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @Author: judy
 * @Description:
 * @Date: Created in 10:58 2019/5/24
 */
public class MyserverInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new MypresonDecoder());
        channelPipeline.addLast(new MyPersonEncoder());
        channelPipeline.addLast(new MyServerHandler());
    }
}
