package com.mmall.controller;

import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/test.do")
    public void test1() {
        System.out.println("asdf");
         String username="张三";

         String password="123456";

         String email="2629704685@qq.com";

         String phone="13500001111";

         String question="问题";

         String answer="答案";

         Integer role=1;
         Date date=new Date();
         User user=new User();

         user.setUsername(username);
         user.setPassword(password);
         user.setEmail(email);
         user.setPhone(phone);
         user.setQuestion(question);
         user.setAnswer(answer);
         user.setRole(role);
         user.setCreateTime(date );
         user.setUpdateTime(date);
        userMapper.insert(user);
    }



}
