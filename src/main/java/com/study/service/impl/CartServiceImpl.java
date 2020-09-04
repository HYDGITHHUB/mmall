package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Cart;
import com.study.entity.Product;
import com.study.mapper.CartMapper;
import com.study.mapper.ProductMapper;
import com.study.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<CartVO> findAllVO(Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<Cart> list = this.cartMapper.selectList(wrapper);
        List<CartVO> result = new ArrayList<>();
        CartVO cartVO = null;
        for (Cart cart : list) {
            Product product = this.productMapper.selectById(cart.getProductId());
            cartVO = new CartVO();
            BeanUtils.copyProperties(product, cartVO);
            BeanUtils.copyProperties(cart, cartVO);
            result.add(cartVO);
        }
        return result;
    }

    @Override
    public boolean updateCart(String type, Integer id, Integer productId, Integer quantity, Float cost) {
        //修改购物车的数量
        Cart cart = this.cartMapper.selectById(id);
        Product product = this.productMapper.selectById(productId);
        Integer val = null;
        //修改商品库存
        switch (type){
            case "sub":
                val = cart.getQuantity() - quantity;
                product.setStock(product.getStock()+val);
                break;
            case "add":
                val = quantity - cart.getQuantity();
                product.setStock(product.getStock()-val);
                break;
        }
        cart.setQuantity(quantity);
        cart.setCost(cost);
        int cartRow = this.cartMapper.updateById(cart);
        int productRow = this.productMapper.updateById(product);
        if(cartRow == 1 && productRow == 1) return true;
        return false;
    }
}
