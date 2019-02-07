package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("addCart.do")
    @ResponseBody
    public ServerResponse addCart(HttpSession session, Integer productId, Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return iCartService.addCart(user.getId(),productId,count);
    }
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getCartList(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return iCartService.getCartList(user.getId());
    }
    //移除购物车某个产品
    @RequestMapping("deleteProduct.do")
    @ResponseBody
    public ServerResponse deleteCart(HttpSession session , String productIds){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return  iCartService.deleteCart(user.getId(), productIds);
    }
    //更新购物车某个产品数量
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse updateCart(HttpSession session , Integer productId,Integer count){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return iCartService.updateCart(user.getId(),productId, count);
    }
    //购物车选中某个商品
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse selectSomeone(HttpSession session ,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }

        return iCartService.selectSomeone(user.getId(),productId);
    }
    @RequestMapping("unselect.do")
    @ResponseBody
    public ServerResponse unselectSomeone(HttpSession session,Integer productId){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }

        return iCartService.unselectSomeone(user.getId(),productId);
    }
//    查询在购物车里的产品数量
    @RequestMapping("getCartProductCount.do")
    @ResponseBody
    public ServerResponse getCartProductCount(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }

        return iCartService.getCartProductCount(user.getId());
    }
    //
    @RequestMapping("selectAll.do")
    @ResponseBody
    public ServerResponse selectAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }

        return iCartService.selectAllOrUnSelectAll(user.getId(),Const.CartChecked.CHECKED);
    }
    @RequestMapping("unSelectAll.do")
    @ResponseBody
    public ServerResponse unSelectAll(HttpSession session){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        return iCartService.selectAllOrUnSelectAll(user.getId(),Const.CartChecked.UN_CHECKED);
    }






}
