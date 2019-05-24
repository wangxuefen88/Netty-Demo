package com.judy.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 10:40 2019/5/24
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder");
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
