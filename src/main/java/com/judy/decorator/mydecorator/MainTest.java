package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:07 2019/5/24
 */
public class MainTest {
    public static void main(String[] args){
        BeDecorated beDecorated = new Decorated1(new BasicStyles());
        beDecorated.dress();
    }
}
