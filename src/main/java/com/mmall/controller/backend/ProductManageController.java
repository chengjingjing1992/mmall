package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;


    @RequestMapping("getProductAdd.do")
    public String getProductAdd(){
        //获取所有的分类id 并且已经排序好

        return "";
    }

    @RequestMapping("saveProduct.do")
    @ResponseBody
    public ServerResponse saveProduct(HttpSession session, Product product){
        //判断用户是否登录
        User user =(User) session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        //判断是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.saveProduct(product);
        }

        return ServerResponse.createByErrorMeg("需要管理员权限");
    }
    @RequestMapping("setSaleStatus.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId,Integer status){
        User user =(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorMeg("用户未登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){

            return iProductService.setSaleStatus(productId, status);

        }
        return ServerResponse.createByErrorMeg("需要管理员权限");
    }
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId,Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.manageProductDetail(productId);

        }else{
            return ServerResponse.createByErrorMeg("无权限操作");
        }

    }
    @RequestMapping("adminDetail.do")
    public String getAdminDetail(HttpServletRequest request,HttpSession session, Integer productId,Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){

            request.setAttribute("serverRes",ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员"));
            return "productdetail";
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            request.setAttribute("serverRes",iProductService.manageProductDetail(productId));
            return "productdetail";

        }else{

            request.setAttribute("serverRes",ServerResponse.createByErrorMeg("无权限操作"));
            return "productdetail";
        }

    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMeg("无权限操作");
        }
    }

    @RequestMapping("adminList.do")
    public String adminProductList(HttpServletRequest req,HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            req.setAttribute("serverRsponse",ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员"));
            return "product_list";

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            req.setAttribute("serverRsponse",iProductService.getProductList(pageNum,pageSize));

            //填充业务
            return "product_list";
        }else{
            req.setAttribute("serverRsponse",ServerResponse.createByErrorMeg("无权限操作"));

            return "product_list";
        }
    }
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMeg("无权限操作");
        }
    }
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile multipartFile, HttpServletRequest request){
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
//        }

//        if(iUserService.checkAdminRole(user).isSuccess()){
            //根据servlet 的上下文 动态的创造出一个相对路径出来
            //我们要上传的文件夹就是这个 upload  项目本地的upload
            String projectLocalPath=request.getSession().getServletContext().getRealPath("upload");
//        C:\\Users\\Administrator\\IdeaProjects\\mmall\target\\mmall\\upload
            System.out.println("path=="+projectLocalPath);
            System.out.println(request.getSession().getServletContext());

            String targetFileName=iFileService.upload(multipartFile,projectLocalPath);
            String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);

        }


//        return ServerResponse.createByErrorMeg("无权限操作");
//    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
//        User user = (User)session.getAttribute(Const.CURRENT_USER);
//        if(user == null){
//            resultMap.put("success",false);
//            resultMap.put("msg","请登录管理员");
//            return resultMap;
//        }
        //富文本中对于返回值有自己的要求,我们使用是simditor所以按照simditor的要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
//        }else{
////            resultMap.put("success",false);
////            resultMap.put("msg","无权限操作");
////            return resultMap;
////        }
    }




}
