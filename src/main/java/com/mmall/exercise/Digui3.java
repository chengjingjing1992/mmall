package com.mmall.exercise;

public class Digui3 {
//    倒序输出一个正整数 倒序输出正整数的各位数
    public static void main(String[] args) {
        new Digui3().printDigit(12345);
    }
//    12345
    public void  printDigit(int n){

        System.out.print(n%10);
        if (n > 10){
            printDigit(n/10);
        }

    }
}
