package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    //声明日志
    private static Logger logger= LoggerFactory.getLogger(TokenCache.class); //选择lof4
    //声明静态的内存块
    private static LoadingCache<String,String> localCache=
                                            //构建本地Cache// 设置缓存初始化容量        //设置有效期12小时
            CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                //默认的数据加载实现 ，当调用get 取值的时候，如果key 没有对应的值，就调用这个方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";//避免控制真
                }
            });

    public  static void setKey(String key,String value){
        localCache.put(key,value);
    }

    public static String getValue(String key){
        String value=null;
        try {
            value=localCache.get(key);
            if("null".equals(value)){
               return null;
            }
            return value;

        }catch (Exception e){
            logger.error("localCache get error" ,e);
        }
        return  null;
    }

}
