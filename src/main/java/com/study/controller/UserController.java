package com.study.controller;


import com.study.entity.User;
import com.study.enums.ResultEnum;
import com.study.exception.MMallException;
import com.study.service.CartService;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

    @PostMapping("/register")
    public String register(User user) {
        if(user == null){
            throw new MMallException(ResultEnum.USER_NOT_EXIST);
        }
        boolean save = this.userService.register(user);
        if(!save){
            throw new MMallException(ResultEnum.USER_SAVE_FAIL);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session){
        if(user == null){
            throw new MMallException(ResultEnum.USER_NOT_EXIST);
        }
        User result = this.userService.login(user);
        if(result == null){
            return "login";
        }else{
            session.setAttribute("user", result);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

}

