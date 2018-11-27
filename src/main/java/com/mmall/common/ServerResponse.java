package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 通用的数据端的响应对象
 *
 * 把响应的信息当作一个 对象
 *
 */
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)//如果data是null 就不需要把数据返回给前端
//保证序列化json 的时候，如果是null的对象 key 也会消失  36：26 分左右 5-1章
public class ServerResponse<T> implements Serializable { //用泛型来声明这个类 并且实现序列化接口
    private int status;
    private String msg;
    private T data;
//    构造方法
    private ServerResponse(int status){
        this.status=status;//这个对象的status属性的值等于 传入的形参
    }
    private ServerResponse(int status,T data){
        this.status=status;
        this.data=data;
    }
    private ServerResponse(int status,String msg,T data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }
    private ServerResponse(int status,String msg){
        this.status=status;
        this.msg=msg;
    }

    @JsonIgnore  //在序列化的时候不会显示出来  如果不加处理 isSuccess 字段也会显示在json 里
    //响应是否成功
    public boolean isSuccess(){
//        return this.status==0;
        return  this.status == ResponseCode.SUCCESS.getCode();//获取枚举类ResponseCode的SUCCESS的code
    }

    //get 方法
    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
    //创建这个对象通过一个成功的
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc());
    }

    public static <T> ServerResponse<T> createBySuccessMeg(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMeg(String errorMsg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }
    //声明一个code是 变量的方法
    public static <T> ServerResponse<T> createByErrorCodeMeg(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }



}
