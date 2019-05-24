package com.judy.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 12:31 2019/5/17
 */
public class Test {
    public static void main(String[] args){
       int cout = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(cout);
    }
}
