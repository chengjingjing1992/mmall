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
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

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



}
