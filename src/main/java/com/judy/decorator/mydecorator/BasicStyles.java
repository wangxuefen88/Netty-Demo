package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:06 2019/5/24
 */
public class BasicStyles implements BeDecorated {

    @Override
    public void dress() {
        System.out.println("我是最基础的装饰,裙子需要有最基础的布料");
    }
}
