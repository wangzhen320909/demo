package com.example.oracle.demo.controller;

import com.example.oracle.demo.dao.UserDao;
import com.example.oracle.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.ws.RespectBinding;
import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private UserDao userDao;

    @RequestMapping("home")
    public String index(){
        return "first index";
    }

    @RequestMapping("getAllList")
    public String getAllList(){
        List<User>  users = userDao.findAllList();
        return users.toString();
    }
}
