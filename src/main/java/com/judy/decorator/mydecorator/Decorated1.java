package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:02 2019/5/24
 */
public class Decorated1 extends Decorated{

    public Decorated1(BeDecorated beDecorated) {
        super(beDecorated);
    }

    @Override
    public void dress() {
        super.dress();
        MyDecorated1();
    }

    public void  MyDecorated1(){
        System.out.println("我是装饰者1,我对装饰者的基础上添加一个花纹");
    }
}
