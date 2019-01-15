package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import jdk.nashorn.internal.parser.Token;

import javax.servlet.http.HttpSession;

public interface IUserService {// 用字母I开头一看就知道是个接口便于维护
    ServerResponse<User> login(String userName, String passWord);
    ServerResponse register(User user);
    ServerResponse checkValid(String value,String type);
    ServerResponse getUserByName(String userName);
    ServerResponse forgetCheckAnswer(String userName,String question,String answer);
    ServerResponse forgetResetPassword(String userName, String passwordNew, String forgetToken);
    ServerResponse resetPassword(String passwordOld, String passwordNew, HttpSession session);
    ServerResponse updateInformation(String email,String phone,String question,String answer,HttpSession session);
    ServerResponse getInformation(Integer userId);
    ServerResponse checkAdminRole(User user);
}
