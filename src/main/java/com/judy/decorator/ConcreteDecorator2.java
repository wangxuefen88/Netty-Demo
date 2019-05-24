package com.judy.decorator;

/**
 * @Author: judy
 * @Description: 具体装饰角色
 * @Date: Created in 11:27 2019/5/13
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
//        super.doSomething();
        this.doAnotherThing();
    }

    /**
     * 过滤流增加额外的功能
     */
    private void doAnotherThing(){
        System.out.println("功能C");
    }
}
