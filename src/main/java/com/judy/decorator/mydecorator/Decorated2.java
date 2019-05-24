package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:02 2019/5/24
 */
public class Decorated2 extends Decorated{

    public Decorated2(BeDecorated beDecorated ) {
        super(beDecorated);
    }

    @Override
    public void dress() {
        super.dress();
        MyDecorated2();
    }

    public void  MyDecorated2(){
        System.out.println("我是装饰者2,我对装饰者的基础上添加粉色");
    }
}
