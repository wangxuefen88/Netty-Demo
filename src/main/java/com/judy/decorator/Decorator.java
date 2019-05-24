package com.judy.decorator;

/**
 * @Author: judy
 * @Description: 装饰角色,持有一个构建角色的引用 , FilterInputStream
 * @Date: Created in 11:25 2019/5/13
 */
public class Decorator implements Component {
    //inputStream
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
