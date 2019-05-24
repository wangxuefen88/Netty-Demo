package com.judy.netty;

/**
 * @Author: judy
 * @Description:
 * @Date: Created in 10:36 2019/5/24
 */
public class PersonProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
