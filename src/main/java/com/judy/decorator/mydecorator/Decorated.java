package com.judy.decorator.mydecorator;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 8:15 2019/5/24
 */
public class Decorated implements BeDecorated {

    private BeDecorated beDecorated;
    public Decorated(BeDecorated beDecorated ) {
        this.beDecorated = beDecorated;
    }

    @Override
    public void dress() {
        this.beDecorated.dress();
    }
}
