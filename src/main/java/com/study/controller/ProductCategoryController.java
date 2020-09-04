package com.study.controller;


import com.study.entity.User;
import com.study.service.CartService;
import com.study.service.ProductCategoryService;
import com.study.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Controller
@RequestMapping("//productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("productCategoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findAllVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }
}

