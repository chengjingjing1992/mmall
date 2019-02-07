package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;
import org.springframework.web.bind.annotation.RequestParam;


public interface IProductService {
    ServerResponse saveProduct(Product product);
    ServerResponse setSaleStatus(Integer productId,Integer status);
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);
    ServerResponse productDetail(Integer productId);
    ServerResponse dynamicProductList( String keyword,Integer categoryId, Integer pageNum,Integer pageSize, String orderBy);
}
