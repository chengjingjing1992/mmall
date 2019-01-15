package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/mange/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("addCategory.do")
    @ResponseBody                                                                                  //如果没有传默认是0
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        //判断用户是否登录
        if(user==null){
            return ServerResponse.createByErrorMeg("用户为登录");
        }
        //判断是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //添加分类
            return iCategoryService.addCategory(categoryName ,parentId);

        }

        return ServerResponse.createByErrorMeg("无权限操作,需要管理员权限");
    }
    @RequestMapping("updateCategoryName.do")//更新分类的名字
    @ResponseBody
    public ServerResponse updateCategoryName(HttpSession session,Integer categoryId,String  categoryName ){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户为登录");
        }
        //改名逻辑
        return iCategoryService.updateCategoryName(categoryId,categoryName);

    }
    @RequestMapping("getChildrenCategoryarallel.do")//获取一个分类的子节点 并且是平级的 并且不递归
    @ResponseBody
    public ServerResponse getChildrenCategoryParallel(HttpSession session,Integer categoryId){
        //判断登录
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户为登录");
        }
        //判断是管理员
        //判断是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //添加分类
            return iCategoryService.getChildrenCategoryParallel(categoryId);

        }

        return ServerResponse.createByErrorMeg("无权限操作,需要管理员权限");
    }









}
