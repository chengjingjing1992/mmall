package com.mmall.exercise;



/**
 * 递归求阶乘
 */
public class Digui {
//    一个正整数的阶乘（factorial）是所有小
// 于及等于该数的正整数的积，并且0的阶乘为1。自然数n的阶乘写作n!。1808年，基斯顿·卡曼引进这个表示法。
//    亦即n!=1×2×3×...×n。阶乘亦可以递归方式定义：0!=1，n!=(n-1)!×n。
    public static void main(String[] args) {
//        int a=1;int temp=1;
//        System.out.println("输入一个数求它的阶乘");
//
//
//        int ok=getDigui(1,1,6);
//        System.out.println(ok);
        int ok=getDigui2(5);
        System.out.println( ok);

    }


    public static int getDigui(int a,int tempresult1 ,int n){

        if((a+1)==n){
            return tempresult1*(a+1);
        }
        return getDigui(a+1,tempresult1*(a+1),n);
    }
    public static int getDigui2( int n){
        if(n==0){
            return 1;
        }
        return  n*getDigui2(n-1);
    }

}
