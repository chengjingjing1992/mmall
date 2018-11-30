package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUserName(String userName);

    int checkEmail(String email);

    User selectLogin(@Param("userName")String userName,@Param("passWord")String passWord);

    User getUserByName(String userName);
                    //这里有多个就得加@Param 不知道为什么
    int checkAnswer(@Param("userName")String userName,@Param("question")String question,@Param("answer")String answer);
                  //这里有多个就得加@Param 不知道为什么
    int updatePassWordByName(@Param("userName")String userName,@Param("passwordNewMD5")String passwordNewMD5);


}