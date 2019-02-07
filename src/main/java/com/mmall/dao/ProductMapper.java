package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectList();

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByNameAndProductId(@Param("productName")String productName,@Param("productId") Integer productId);

    List<Product> selectProductListByIdAndKeyword(@Param("keyword")String keyword,@Param("idList")List<Integer> idList);

    List<Product> selectByUserId(Integer userId);


}