package com.mmall.service;

import com.mmall.common.ServerResponse;

public interface IOrderService {
    ServerResponse list(Integer userId,Integer pageNum,Integer pageSize);
}
