package com.example.oracle.demo.dao.impl;

import com.example.oracle.demo.dao.UserDao;
import com.example.oracle.demo.dao.mapper.UserMapper;
import com.example.oracle.demo.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userDao")
public class UserDaoImpl implements UserDao {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> findAllList() {
        return userMapper.findAllList();
    }
}
