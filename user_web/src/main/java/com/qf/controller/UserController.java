package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IEmailService;
import com.qf.service.IUserService;
import org.apache.zookeeper.Login;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Reference
    IUserService userService;
    @Reference
    IEmailService emailService;
        @RequestMapping("/register")
        public Object register(User user, HttpServletRequest request, Model model){
            System.out.println(user);
           String code =(String) request.getSession().getAttribute("code");
           if(user.getCode().equals(code)){
               userService.register(user);
               model.addAttribute("msg","");
               return "login";
           }
           model.addAttribute("msg","注册失败,请重新注册");
            return "register";
        }
        @RequestMapping("/sendEmail")
        @ResponseBody
        public Object sendEmail(HttpServletRequest request, @RequestParam String adr){
            System.out.println(adr);
            Random random = new Random();
            int i = random.nextInt(8999)+1000;
            request.getSession().setAttribute("code",i+"");
            Email email = new Email();
            email.setFrom("yzh960813@sina.com");
            email.setTo(adr);
            email.setSentDate(new Date());
            email.setSubject("验证码");
            email.setText(i+"");
            emailService.sendEmail(email);
            return  null;
        }

    @RequestMapping("/findPsw")
    public Object findPsw( String name){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        List<User> users = userService.getUserByName(map);
        if(users==null){
            return "findPsw";
        }
       User user = users.get(0);
        String adr = user.getEmail();
        Email email =new Email();
        email.setFrom("yzh960813@sina.com");
        email.setTo(adr);
        email.setSentDate(new Date());
        email.setSubject("密码找回");
        email.setText("请点击<a href='http://10.36.138.209:8080/user/togetPsw?name="+name+" '>这里</a>找回密码~");
        emailService.sendEmail(email);
        return  "login";
    }
    @RequestMapping("/tofindPsw")
    public Object tofindPsw(){
        return  "findPsw";
    }
    @RequestMapping("/togetPsw")
    public Object togetPsw(@RequestParam String name,Model model){
            model.addAttribute("name" ,name);
        return  "updatePsw";
    }
    @RequestMapping("/updatePsw")
    public Object updatePsw(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("name",user.getName());
        List<User> users = userService.getUserByName(map);
        User user1 = users.get(0);
        user1.setPassword(user.getPassword());
        userService.updateById(user1);
        return  "login";
    }
    @RequestMapping("/toRegister")
    public Object toRegister(){
        return  "register";
    }
    @RequestMapping("/toLogin")
    public Object toLogin(){
        return  "login";
    }
    @RequestMapping("/login")
    public Object login(@RequestParam String name,@RequestParam String password){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("password",password);
        List<User> users = userService.getUserByName(map);
        if(users!=null){
            return  "ok";
        }
        return  "login";
    }
}
