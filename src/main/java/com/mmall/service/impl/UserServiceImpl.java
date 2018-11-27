package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired//通过这个注解把mapper 注解进来
    private UserMapper userMapper;

    @Transactional  //这里要加这个我也不知道为啥
    public ServerResponse<User> login(String userName, String passWord) {
        int resultCount=userMapper.checkUserName(userName);
        //用户名未找到
        if (resultCount==0){
            return ServerResponse.createByErrorMeg("该用户不存在");
        }

        User user=userMapper.selectLogin(userName,passWord);
        //用户名找到了 ，但是密码不对
        if (user == null){
            return ServerResponse.createByErrorMeg("密码错误");
        }
        //用户名密码都正确 将密码MD5处理后再返回
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("deng录成功",user);
    }


}
