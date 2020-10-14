package com.edu.sso.controller;

import com.edu.bean.TbUser;
import com.edu.common.bean.ShoppingResult;
import com.edu.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/showLogin")
    public String showLogin(String redirect, Model model){
        model.addAttribute("redirect",redirect);
        return "login";
    }

    @RequestMapping("/showRegister")
    public String showRegister(){
        return "register";
    }

    @ResponseBody
    @RequestMapping("/check/{param}/{type}")
    public Object check(@PathVariable String param,@PathVariable int type,String callback){
        ShoppingResult result = null;
        if (1 != type && 2 != type && 3 != type){
            return ShoppingResult.build(400,"");
        }
        if (null != result){
            if (StringUtils.isEmpty(callback)){
                return result;
            }else {
                MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
                jacksonValue.setJsonpFunction(callback);
                return jacksonValue;
            }
        }
        result = userService.getUserByNameAndType(param,type);

            if (StringUtils.isEmpty(result)){
                return result;
            }else {
                MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
                jacksonValue.setJsonpFunction(callback);
                return jacksonValue;
            }
    }

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ShoppingResult register(TbUser user){
        return userService.saveUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ShoppingResult login(TbUser user, HttpServletRequest request, HttpServletResponse response){
        return userService.login(user,request,response);
    }

    @ResponseBody
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    public Object token(@PathVariable String token,String callback){
        TbUser user = userService.getUserByToken(token);
        if (StringUtils.isEmpty(callback)){
           return ShoppingResult.ok(user);
        }else {
            MappingJacksonValue jacksonValue = new MappingJacksonValue(ShoppingResult.ok(user));
            jacksonValue.setJsonpFunction(callback);
            return jacksonValue;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/logout/{token}",method = RequestMethod.GET)
    public Object logout(@PathVariable String token,String callback){
       ShoppingResult result = userService.logout(token);
       if (StringUtils.isEmpty(callback)){
           return null;
       }else {
           MappingJacksonValue jacksonValue = new MappingJacksonValue(ShoppingResult.ok(result));
           jacksonValue.setJsonpFunction(callback);
           return jacksonValue;
       }
    }
}
