package com.mmall.service;
import com.mmall.common.ServerResponse;
import org.apache.ibatis.annotations.Param;

public interface ICartService {
    ServerResponse addCart(Integer userId,Integer productId,Integer count);

    ServerResponse getCartList(Integer userId);

    ServerResponse updateCart(@Param("userId")Integer userId,@Param("productId")Integer productId,@Param("count")Integer count);

    ServerResponse deleteCart(@Param("userId")Integer userId, @Param("productIds")String productIds);

    ServerResponse selectSomeone(Integer userId,Integer productId);

    ServerResponse unselectSomeone(Integer userId,Integer productId);

    ServerResponse getCartProductCount(Integer userId);

    ServerResponse selectAllOrUnSelectAll(Integer userId ,Integer checked);
}
