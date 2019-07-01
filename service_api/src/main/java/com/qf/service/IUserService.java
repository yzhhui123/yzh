package com.qf.service;

import com.qf.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService  {
    int register(User user);
    List<User> getUserByName(Map<String,Object> map);
    int updateById(User user);
}
