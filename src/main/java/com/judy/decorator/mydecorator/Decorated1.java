package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:02 2019/5/24
 */
public class Decorated1 implements BeDecorated{
    private BeDecorated beDecorated;
    public Decorated1(BeDecorated beDecorated ) {
        this.beDecorated = beDecorated;
    }

    @Override
    public void dress() {
        this.beDecorated .dress();
        MyDecorated1();
    }

    public void  MyDecorated1(){
        System.out.println("我是装饰者1,我对装饰者的基础上添加一个花纹");
    }
}
