package com.example.encrypt.test;

/**
 * @author zhangzhongyuan@szanfu.cn
 * @description
 * @since 2022/9/27 15:49
 */
public class AsciiDemo {
    public static void main(String[] args) {
        char a = 65;
        System.out.println(a);

        String str = "AaZ";
        //拆开
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            System.out.println((int) aChar);
        }

    }
}
