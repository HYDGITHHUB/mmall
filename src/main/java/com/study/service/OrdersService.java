package com.study.service;

import com.study.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.entity.User;
import com.study.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface OrdersService extends IService<Orders> {
    public Orders create(String selectAddress, Float cost, User user,String address,String remark);
    public List<OrdersVO> getAllByUserId(Integer userId);
}
