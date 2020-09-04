package com.study.controller;


import com.study.entity.User;
import com.study.service.CartService;
import com.study.service.ProductCategoryService;
import com.study.service.ProductService;
import com.study.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
@RestController
@RequestMapping("//product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list/{type}/{id}")
    public ModelAndView list(@PathVariable("type") Integer type, @PathVariable("id") Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("productList",this.productService.findAllByProductCategory(type, id));
        modelAndView.addObject("productCategoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findAllVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id") Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("product",this.productService.getById(id));
        modelAndView.addObject("productCategoryList",this.productCategoryService.getAllProductCategoryVO());
        List<CartVO> carts = new ArrayList<>();
        if(user != null){
            carts = this.cartService.findAllVO(user.getId());
        }
        modelAndView.addObject("carts",carts);
        return modelAndView;
    }
}

