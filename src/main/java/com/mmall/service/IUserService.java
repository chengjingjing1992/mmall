package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

public interface IUserService {// 用字母I开头一看就知道是个接口便于维护
    ServerResponse<User> login(String userName, String passWord);
}
