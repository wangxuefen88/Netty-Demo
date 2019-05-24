package com.judy.decorator;

import java.io.*;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 11:33 2019/5/13
 */
public class Client {
    public static void main(String[] args){
        //体现动态功能,进行扩展
        //与IO是一样  功能A 功能C 功能B
//        Component component = new ConcreteDecorator1(new ConcreteDecorator2(new ConcreteComponent()));
//        component.doSomething();
        //功能A 功能B
        Component component = new ConcreteDecorator2(new ConcreteComponent());
        component.doSomething();
        //功能A
//        Component component = new ConcreteComponent();
//        component.doSomething();

        InputStream inputStream = new BufferedInputStream(new PipedInputStream());

    }
}
