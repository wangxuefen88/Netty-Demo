package com.judy.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: judy
 * @Description: 编码器
 * @Date: Created in 10:37 2019/5/24
 */
public class MypresonDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MypresonDecoder decode invoked");

        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(length);
        personProtocol.setContent(content);
        out.add(personProtocol);
    }
}
