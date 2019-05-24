package com.judy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.ConnectException;
import java.nio.charset.Charset;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 10:53 2019/5/24
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            String messagerToBeSent = "send from client";
            byte[] bytes = messagerToBeSent.getBytes(Charset.forName("utf-8"));
            int length = messagerToBeSent.getBytes(Charset.forName("utf-8")).length;
            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setContent(bytes);
            personProtocol.setLength(length);
            ctx.writeAndFlush(personProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("客户端收到消息");

        System.out.println("length"+length);
        System.out.println("content"+ content);
        System.out.println("客户端接受到了数据");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
