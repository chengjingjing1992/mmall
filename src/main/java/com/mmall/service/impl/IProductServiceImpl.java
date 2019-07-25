package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iProductService")
public class IProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService categoryService;

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

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if(productId==null||status==null){
            return ServerResponse.createBySuccessMeg("参数类型错误");
        }
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount=productMapper.updateByPrimaryKeySelective(product);
        if(rowCount>0){
            return ServerResponse.createBySuccess("上下架状态更新成功");
        }
        return ServerResponse.createBySuccess("上下架状态更新失败");
    }
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.createByErrorMeg("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setCategoryName("");
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category!=null){
            productDetailVo.setCategoryName(category.getName());
        }
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));


        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //startPage--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        System.out.println(productList.size());

        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product){
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        Category category=categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category!=null){
            productListVo.setCategoryName(category.getName());
        }else {
            productListVo.setCategoryName("");
        }
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
            System.out.println(productName);
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product productItem : productList){
            ProductListVo productListVo = assembleProductListVo(productItem);
            productListVoList.add(productListVo);
        }
        PageInfo pageResult = new PageInfo(productListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse productDetail(Integer productId){
        if(productId == null){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null||product.getStatus()!= Const.productStatusOnSale.ON_SALE.getCode()){
            return ServerResponse.createByErrorMeg("产品已下架或者删除");
        }

        return ServerResponse.createBySuccess(product);
    }

    @Override
    public ServerResponse dynamicProductList(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        //如果keyword 和 categoryId 都没有传
        if(StringUtils.isBlank(keyword)&& categoryId==null ){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        PageHelper.startPage( pageNum,pageSize);
        if(StringUtils.isNotBlank(orderBy)&&Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            String [] arry=orderBy.split("_");
            //进行分页
            PageHelper.orderBy(arry[0]+" "+arry[1]);
        }

//        if(categoryId!=null&&categoryMapper.selectByPrimaryKey(categoryId)!=null){
            List<Integer> idList=(List) categoryService.getCategoryAndDeepChildrenCategory(categoryId).getData();
            keyword=new StringBuilder().append("%")+keyword+"%";
        System.out.println(keyword);
            List<Product> productList=productMapper.selectProductListByIdAndKeyword(keyword,idList);
            PageInfo pageInfo=new PageInfo(productList);
            return ServerResponse.createBySuccess(pageInfo);
//        }

//        return null;
    }

}
