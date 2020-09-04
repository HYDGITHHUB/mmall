package com.study.service;

import com.study.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllVO(Integer userId);
    public boolean updateCart(String type,Integer id,Integer productId,Integer quantity,Float cost);
}
