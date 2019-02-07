package com.mmall.controller.portal;


import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/procuct/")
public class ProcuctController {

    @Autowired
    private IProductService productService;

    @RequestMapping(value = "productDetal.do")
    @ResponseBody
    public ServerResponse getProductDetal(HttpSession session, Integer productId){
        //判断用户是否登录
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return productService.productDetail(productId);
    }
    @RequestMapping("dynamicProductList.do")
    @ResponseBody
    public ServerResponse dynamicProductList(HttpSession session,
                                             @RequestParam(value = "keyword",required = false)String keyword,
                                             @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                             @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                             @RequestParam(value = "orderBy",required = false)String orderBy){
        //判断用户是否登录
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        ServerResponse serverResponse=productService.dynamicProductList(keyword,categoryId,pageNum,pageSize,orderBy);

        return serverResponse;
    }



}
