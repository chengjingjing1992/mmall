package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.CookieStore;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    //登录
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)//method = RequestMethod.POST 这个请求只能通过post 请求过来
    @ResponseBody  //返回的时候 自动通过spring MVC 的jackson 插件 将我们的返回值序列化成json  配置参见dispatcher-servlet.xml
    public ServerResponse<User> login(String userName, String passWord, HttpSession session,HttpServletResponse response){
        ServerResponse<User> userServerResponse=iUserService.login(userName,passWord);
        if(userServerResponse.isSuccess()==true){
            session.setAttribute(Const.CURRENT_USER,userServerResponse.getData());
            Cookie cookie=new Cookie("sessionId",session.getId());
            cookie.setPath("/");//("/")表示的是访问当前工程下的所有webApp都会产生cookie
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        }
        System.out.println(userServerResponse.toString());
        return userServerResponse;
    }
    @RequestMapping(value = "/adminLogin.do",method = RequestMethod.POST)//method = RequestMethod.POST 这个请求只能通过post 请求过来
    @ResponseBody  //返回的时候 自动通过spring MVC 的jackson 插件 将我们的返回值序列化成json  配置参见dispatcher-servlet.xml
    public ModelAndView adminLogin(String userName, String passWord, HttpSession session){
        ServerResponse<User> userServerResponse=iUserService.login(userName,passWord);
        if(userServerResponse.isSuccess()==true){
            session.setAttribute(Const.CURRENT_USER,userServerResponse.getData());
        }
        System.out.println(userServerResponse.toString());
        ModelAndView modelAndView=new ModelAndView("login");
        modelAndView.addObject(userServerResponse);
        return modelAndView;
    }
    //登出
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);

        return ServerResponse.createBySuccess();
    }
    //注册
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse  register(User user){

        return iUserService.register(user);
    }
    //这是用于校验的接口
    @RequestMapping(value = "/checkValid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse checkValid(String value,String type){
        return iUserService.checkValid(value,type);
    }

    //获取登录用户信息/user/getUserInfo.do
    @RequestMapping(value = "/getUserInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user=(User) session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            //返回登录用户的信息
           return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMeg("用户未登录");
    }

    //忘记密码
    ///user/forget_get_question.do
    @RequestMapping(value = "/forgetGetQuestion.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> forgetGetQuestion(String userName){
        return iUserService.getUserByName(userName);
    }

//    提交问题答案 /user/forgetCheckAnswer.do
    @RequestMapping(value = "/forgetCheckAnswer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String userName,String question,String answer){

        return iUserService.forgetCheckAnswer( userName, question, answer);
    }

    //忘记密码的重设密码
    ///user/forget_reset_password.do
    @RequestMapping(value = "/forgetResetPassword.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse forgetResetPassword(String userName, String passwordNew, String forgetToken){
        return iUserService.forgetResetPassword( userName,  passwordNew,  forgetToken);
    }

//    登录中状态重置密码/user/reset_password.do
    @RequestMapping(value = "/resetPassword.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse resetPassword(String passwordOld, String passwordNew,HttpSession session){
        return iUserService.resetPassword( passwordOld,  passwordNew,session);
    }

//    登录状态更新个人信息
///user/update_information.do
    @RequestMapping(value = "/updateInformation.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse updateInformation(String email,String phone,String question,String answer,HttpSession session){

        return iUserService.updateInformation( email, phone, question, answer,session);
    }
//    /user/get_information.do获取当前登录用户的详细信息，并强制登录
    @RequestMapping(value = "/getInformation.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getInformation(HttpSession session){

        User user=(User) session.getAttribute(Const.CURRENT_USER);

        if(user!=null){
            return iUserService.getInformation(user.getId());
        }
        return ServerResponse.createByErrorCodeMeg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,无法获取当前用户信息,status=10,强制登录");
    }

}