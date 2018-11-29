package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    //登录
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)//method = RequestMethod.POST 这个请求只能通过post 请求过来
    @ResponseBody  //返回的时候 自动通过spring MVC 的jackson 插件 将我们的返回值序列化成json  配置参见dispatcher-servlet.xml
    public ServerResponse<User> login(String userName, String passWord, HttpSession session){
        ServerResponse<User> userServerResponse=iUserService.login(userName,passWord);
        if(userServerResponse.isSuccess()==true){
            session.setAttribute(Const.CURRENT_USER,userServerResponse.getData());
        }
        System.out.println(userServerResponse.toString());
        return userServerResponse;
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

        return null;
    }



}
