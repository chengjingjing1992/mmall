package com.mmall.common;

/**
 * 这个类是我用来学习用枚举类
 */
public enum MyEnumTest {
    HELLO(0,"nihao"),BYE(1,"oooo");


    private int code;
    private String mes;

    MyEnumTest(int code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public int getCode() {
        return code;
    }

    public String getMes() {
        return mes;
    }

    public static void main(String[] args) {
        System.out.println(MyEnumTest.HELLO.getCode());
        System.out.println(MyEnumTest.HELLO.getMes());
    }
}
