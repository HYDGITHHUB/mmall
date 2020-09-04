package com.study.controller;


import com.study.entity.Orders;
import com.study.entity.User;
import com.study.service.CartService;
import com.study.service.OrdersService;
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
@RequestMapping("//orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CartService cartService;

    @PostMapping("/create")
    public ModelAndView create(String selectAddress, Float cost, HttpSession session,String address,String remark){
        User user = (User) session.getAttribute("user");
        Orders orders = this.ordersService.create(selectAddress, cost, user,address,remark);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("orders",orders);
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView getAllByUserId(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("orderList",this.ordersService.getAllByUserId(user.getId()));
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

}

