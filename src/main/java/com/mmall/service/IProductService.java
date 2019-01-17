package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import org.springframework.stereotype.Service;


public interface IProductService {
    ServerResponse saveProduct(Product product);
}
