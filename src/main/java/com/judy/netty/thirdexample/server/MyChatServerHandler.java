package com.judy.netty.thirdexample.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: judy
 * @Description: 服务端处理器
 * @Date: Created in 11:09 2019/5/11
 */
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 存放连接之后Channel(客户端)
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 具体业务逻辑
         */
        channelGroup.forEach(ch->{
            if (channel!=ch) {
                ch.writeAndFlush(channel.remoteAddress() + "[别人发送的消息]" + msg+"\n");
            }else {
                ch.writeAndFlush( "[自己发送的消息]" + msg+"\n");
            }
        });

    }

    /**
     * 服务端与客户端建立好连接
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        /**
         * 先writeAndFlush再add的原因是排除第一个连接,从第二个连接开始广播
         */
        channelGroup.writeAndFlush("[服务器端发送加入]-" + channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }

    /**
     * 连接断开
     *
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器端发送离开-" + channel.remoteAddress());
        //Netty会自动调用,也可不写
        channelGroup.remove(channel);
        System.out.println(channelGroup.size());
    }

    /*
      表示连接处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线");
    }

    /***
     * 表示连接下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "连接下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception");
    }
}
