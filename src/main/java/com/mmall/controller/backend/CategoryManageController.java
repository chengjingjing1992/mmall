package com.mmall.controller.backend;

import com.google.gson.JsonObject;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.PublicKey;
import java.util.*;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired//通过这个注解把mapper 注解进来
     private CategoryMapper categoryMapper;




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
    public ServerResponse getChildrenCategoryParallel(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
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

    //查询当前id的节点 和 递归获取所有 子节点
    @RequestMapping("getCategoryAndDeepChildrenCategory.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId ){

        //判断登录
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户为登录");
        }
        //判断是管理员
        //判断是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){

            return iCategoryService.getCategoryAndDeepChildrenCategory(categoryId);

        }

        return ServerResponse.createByErrorMeg("无权限操作,需要管理员权限");
    }
    //查询当前id的节点 和 递归获取所有 子节点 并且进行排序
    @RequestMapping("getCategoryAndDeepChildrenCategorySort.do")
    public  String getCategoryAndDeepChildrenCategorySort(HttpServletRequest request){
        //查询所有的节点 和 递归获取所有 子节点
        List<Integer> idList= (List) iCategoryService.getCategoryAndDeepChildrenCategory(0).getData();
        //每一个 分类id  parentid  categoryName 存到map 然后一起存到List
        List sortCategoryList=new ArrayList();

        Map map=null;
        for (Integer i:idList
             ) {
            map=new HashMap<>();
            Category category=categoryMapper.selectByPrimaryKey(i);

//            if(category!=null){
                map.put("id",category.getId().toString());
                map.put("text",category.getName());
                if(category.getParentId()==null){
                    map.put("parentId","");
                }else {
                    map.put("parentId",category.getParentId().toString());
                }


//            }
            System.out.println("map"+map.toString());
            sortCategoryList.add(map);
            System.out.println("size="+sortCategoryList.size());
        }


        Object object=MultipleTree.sort(sortCategoryList);
        request.setAttribute("nodes",object.toString());


        return "product_add";
    }
    @RequestMapping("sort.do")
    public String sortAll(HttpServletRequest request){
        List list =new ArrayList();
        list=sort(list,0);
        List list1 =new ArrayList();
        for (Object id:list
             ) {
            list1.add(categoryMapper.selectByPrimaryKey((Integer) id));
        }
        request.setAttribute("categorys",list1);

        return "product_add";
    }


//    List list=new ArrayList();
    int index=0;
    int level=-1;
    public  List sort(List list,Integer id){
        if(id==0){
            list.add(id);
            index=list.indexOf(id);
            level=level+1;
            categoryMapper.selectByPrimaryKey(id).setLevel(level);
        }else {
            index=list.indexOf(id);
            level=level+1;
            categoryMapper.selectByPrimaryKey(id).setLevel(level);
        }
        list=list.subList(0,index+1);
        List<Category> idList=categoryMapper.getChildrenCategoryParallel(id);
        if(idList!=null){
            for (Category category:idList
            ) {
                list.add(category.getId());
                sort(list,category.getId());
            }
        }
        return list;
    }













}
