package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//        String md5Password = MD5. 这里应该加密后去查询
        String md5Password =MD5Util.MD5EncodeUtf8(passWord);

        User user=userMapper.selectLogin(userName,md5Password);
        //用户名找到了 ，但是密码不对
        if (user == null){
            return ServerResponse.createByErrorMeg("密码错误");
        }
        //用户名密码都正确 将密码置空 处理后再返回
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("deng录成功",user);
    }

    @Transactional  //这里要加这个我也不知道为啥
    public ServerResponse register(User user){
        ServerResponse serverResponse=this.checkValid(user.getUsername(),Const.USERNAME);
        //如果校验失败
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }
        serverResponse=this.checkValid(user.getEmail(),Const.EMAIL);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        serverResponse=this.checkValid(user.getPhone(),Const.PHONE);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }


        //注册默认普通用户=0
        user.setRole(Const.COMMON_CUSTOMER);

        //密码进行MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int a=0;
        try {
            a=userMapper.insert(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        //如果插入成功
        if(a==1){
            return ServerResponse.createBySuccess();
        }
        //否则
        return ServerResponse.createByError();
    }
    @Transactional
    public ServerResponse checkValid(String value,String type){
        if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                //判断用户是否已经存在
                int resultCount=userMapper.checkUserName(value);
                System.out.println("resultCount==========="+resultCount);
                if (resultCount!=0){
                    return ServerResponse.createByErrorMeg("该用户名已存在，请更换名称注册");
                }
            }

            if(Const.EMAIL.equals(type)){
                //判断邮箱是否存在
                int resultEmailCount=userMapper.checkEmail(value);
                if (resultEmailCount!=0){
                    return ServerResponse.createByErrorMeg("该邮箱已存在，请更换邮箱注册");
                }

            }
            if(Const.PHONE.equals(type)){
                //判断手机号格式
                boolean flag = false;
                try {
                    // 13********* ,15********,18*********
                    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

                    Matcher m = p.matcher(value);
                    flag = m.matches();

                } catch (Exception e) {
                    flag = false;
                }
                if(flag==false){
                    return ServerResponse.createByErrorMeg("手机号格式不正确");
                }

            }

        }

        return ServerResponse.createBySuccess();
    }


}
