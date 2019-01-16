package com.mmall.exercise;

import jdk.nashorn.internal.parser.Scanner;

/**
 * 递归求阶乘
 */
public class Digui {
//    一个正整数的阶乘（factorial）是所有小
// 于及等于该数的正整数的积，并且0的阶乘为1。自然数n的阶乘写作n!。1808年，基斯顿·卡曼引进这个表示法。
//    亦即n!=1×2×3×...×n。阶乘亦可以递归方式定义：0!=1，n!=(n-1)!×n。
    public static void main(String[] args) {
        int a=1;int temp=1;
        System.out.println("输入一个数求它的阶乘");


        int ok=getDigui(1,1,5);
        System.out.println(ok);


    }


    public static int getDigui(int a,int tempresult1 ,int n){
        //a*(a+1)=result   a+1=b result* (b+1)
       int b=a+1;
        tempresult1=tempresult1*(a+1);
        if(b==n){
            return tempresult1;
        }

        return getDigui(b,tempresult1,n);
    }

}
