package com.study.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.User;
import com.study.entity.UserAddress;
import com.study.service.CartService;
import com.study.service.UserAddressService;
import com.study.vo.AddressVO;
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
@RequestMapping("//userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/info")
    public ModelAndView info(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        modelAndView.addObject("userAddressList",this.userAddressService.list(wrapper));
        modelAndView.addObject("carts",this.cartService.findAllVO(user.getId()));
        return modelAndView;
    }

    @PostMapping("/add")
    @ResponseBody
    public String add(@RequestBody AddressVO addressVO){
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(addressVO.getUserid());
        userAddress.setAddress(addressVO.getAddress());
        userAddress.setRemark(addressVO.getRemark());
        if(addressVO.getIsdefault()) userAddress.setIsdefault(1);
        else userAddress.setIsdefault(0);
        boolean row = this.userAddressService.save(userAddress);
        if(row) return "ok";
        return "fail";
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserAddress> list(){
        return this.userAddressService.list();
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody
    public String del(@PathVariable("id") Integer id){
        boolean result = this.userAddressService.removeById(id);
        if(result) return "ok";
        return "fail";
    }

    @GetMapping("/findById/{id}")
    @ResponseBody
    public UserAddress findById(@PathVariable("id") Integer id){
        return this.userAddressService.getById(id);
    }

}

