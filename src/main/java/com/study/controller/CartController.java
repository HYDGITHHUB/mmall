package com.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Cart;
import com.study.entity.Product;
import com.study.entity.User;
import com.study.entity.UserAddress;
import com.study.service.CartService;
import com.study.service.ProductService;
import com.study.service.UserAddressService;
import com.study.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
@RequestMapping("//cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/add/{id}/{price}/{quantity}")
    public String add(
            @PathVariable("id") Integer id,
            @PathVariable("price") Float price,
            @PathVariable("quantity") Integer quantity,
            HttpSession session){
        Float cost = price*quantity;
        User user = (User) session.getAttribute("user");
        Cart cart = new Cart();
        cart.setProductId(id);
        cart.setCost(cost);
        cart.setQuantity(quantity);
        cart.setUserId(user.getId());
        this.cartService.save(cart);
        //建库存
        Product product = this.productService.getById(id);
        product.setStock(product.getStock()-quantity);
        this.productService.updateById(product);
        return "redirect:/cart/cartVO";
    }

    @GetMapping("/cartVO")
    public ModelAndView cartVOS(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

    @PostMapping("/updateCart/{type}/{id}/{productId}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(
            @PathVariable("type") String type,
            @PathVariable("id") Integer id,
            @PathVariable("productId") Integer productId,
            @PathVariable("quantity") Integer quantity,
            @PathVariable("cost") Float cost
    ){
        boolean result = this.cartService.updateCart(type, id, productId, quantity, cost);
        if(result) return "success";
        return "fail";
    }

    @GetMapping("/removeCart/{id}")
    public synchronized String removeCart(@PathVariable("id") Integer id){
        //修改库存
        Cart cart = this.cartService.getById(id);
        Product product = this.productService.getById(cart.getProductId());
        product.setStock(product.getStock()+cart.getQuantity());
        this.productService.updateById(product);
        //删除购物车
        this.cartService.removeById(id);
        return "redirect:/cart/cartVO";
    }

    @GetMapping("/goToSettlement2")
    public ModelAndView goToSettlement2(HttpSession session){
        User user = (User) session.getAttribute("user");
        //获取购物车记录
        List<CartVO> cartVOList = this.cartService.findAllVO(user.getId());
        //地址信息
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        List<UserAddress> userAddressList = this.userAddressService.list(wrapper);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        modelAndView.addObject("cartVOList",cartVOList);
        modelAndView.addObject("userAddressList",userAddressList);
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

}

