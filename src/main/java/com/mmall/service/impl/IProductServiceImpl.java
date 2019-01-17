package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class IProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse saveProduct(Product product) {
        //如果子图不为空  那么将子图的第一个图赋值给主图
        if(StringUtils.isNotBlank(product.getSubImages())){
            String [] arr=product.getSubImages().split(",");
            product.setMainImage(arr[0]);
        }




        //判断是否传入id
        if(product.getId()!=null){
            //进行更新操作
            int updateResult=productMapper.updateByPrimaryKey(product);
            System.out.println("updateResult="+updateResult);
            if(updateResult==1){

                return ServerResponse.createBySuccessMeg("更新成功");
            }
            return ServerResponse.createBySuccessMeg("更新商品失败");

        }

        int result =productMapper.insert(product);
        if(result==1){
            return ServerResponse.createBySuccessMeg("添加产品成功");
        }
        return ServerResponse.createByErrorMeg("添加产品失败");
    }
}
