package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int register(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> getUserByName(Map<String, Object> map) {
        return userMapper.selectByMap(map);
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateById(user);
    }
}
