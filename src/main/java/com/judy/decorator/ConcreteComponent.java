package com.judy.decorator;

/**
 * @Author: judy
 * @Description: 具体构建角色
 * @Date: Created in 11:22 2019/5/13
 */
public class ConcreteComponent implements Component {
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
