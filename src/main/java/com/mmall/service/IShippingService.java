package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;

public interface IShippingService {
    ServerResponse addShippingAddress(Shipping shipping);
    ServerResponse<String> del(Integer userId,Integer shippingId);
    ServerResponse update(Shipping shipping);
    ServerResponse select(Integer userId,Integer shippingId );
    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);
}
