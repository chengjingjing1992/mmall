package com.mmall.exercise;

/**
 * 菲波那切数列长这个样子：{1 1 2 3 5 8 13 21 34 55..... M }
 * 求第n项
 */
public class Digui1 {


    public static void main(String[] args) {
        int ok=febo( 1, 1, 3);
        System.out.println(ok);

    }
    public static int febo(int first,int second,int n){

        if ((n-3)==0){
            return first+second;
        }
        return febo(second,first+second,n-1);
    }
}
