package com.judy.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Author: judy
 * @Description: 编解码和解码的见解
 * @Date: Created in 9:00 2019/5/16
 */

public class NioTest13 {
    public static void main(String[] args) throws IOException {
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        long length = new File(inputFile).length();
        FileChannel InputChannel = inputRandomAccessFile.getChannel();
        FileChannel outPutChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = InputChannel.map(FileChannel.MapMode.READ_ONLY, 0, length);

        System.out.println("===========================");

        //打印出所支持的字符集编码
        Charset.availableCharsets().forEach((k,v)->{
            System.out.println(k+","+v);
        });


        System.out.println("===========================");

        //生成一个字符集
//        Charset charset = Charset.forName("utf-8");

         Charset charset = Charset.forName("iso-8859-1"); //这个是针对于英文的,当遇到中文的时候会路乱码

        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();



        //解码
        CharBuffer charBuffer = decoder.decode(inputData);

        ByteBuffer outBuffer = Charset.forName("utf-8").encode(charBuffer);
        //编码
//        ByteBuffer outBuffer = encoder.encode(charBuffer);

        outPutChannel.write(outBuffer);
        inputRandomAccessFile.close();
    }
}
