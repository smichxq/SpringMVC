package com.example.springmvc.mapper;

import com.example.springmvc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int insertUser(@Param("user") User user);

    int deleteUser(@Param("userId") int userId);

    int updateName(int userId, String userName);

    int updateAge(int userId, int userAge);

    ArrayList<User> getUsers();

    ArrayList<User> getUsersForLimit(int limit,int offset);

    User getUser(int userId);

    int getUserCount();

    int userCheckByAccount(String userAcc);

    List<Map<String, String>> userCheckByAccName(String userName, String userAcc);

}
