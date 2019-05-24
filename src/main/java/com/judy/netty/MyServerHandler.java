package com.judy.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 10:42 2019/5/24
 */
public class MyServerHandler  extends SimpleChannelInboundHandler<PersonProtocol>{
    private int count;
      @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
          int length = msg.getLength();
          byte[] content = msg.getContent();
          System.out.println(length);
          System.out.println(content);

          String responseMessage = UUID.randomUUID().toString();
          int length1 = responseMessage.getBytes("utf-8").length;
          byte[] bytes = responseMessage.getBytes("utf-8");
          PersonProtocol personProtocol = new PersonProtocol();
          personProtocol.setContent(bytes);
          personProtocol.setLength(length1);
          ctx.writeAndFlush(personProtocol);
      }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
