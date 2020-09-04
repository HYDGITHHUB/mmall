package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.*;
import com.study.mapper.*;
import com.study.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.vo.OrderDetailVO;
import com.study.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Orders create(String selectAddress, Float cost, User user,String address,String remark) {
        //判断当前是新地址还是老地址
        if(selectAddress.equals("newAddress")){
            selectAddress = address;
            //存入数据库
//            QueryWrapper wrapper = new QueryWrapper();
//            wrapper.eq("user_id", user.getId());
//            wrapper.eq("isdefault", 1);
//            UserAddress oldDefault = this.userAddressMapper.selectOne(wrapper);
//            oldDefault.setIsdefault(0);
//            this.userAddressMapper.updateById(oldDefault);
            this.userAddressMapper.clearDefault(user.getId());

            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(user.getId());
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setIsdefault(1);
            this.userAddressMapper.insert(userAddress);
        }
        //存储Orders
        Orders orders = new Orders();
        String seriaNumber = null;
        try {
            StringBuffer result = new StringBuffer();
            for(int i=0;i<32;i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber =  result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);
        orders.setCost(cost);
        orders.setUserAddress(selectAddress);
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        this.ordersMapper.insert(orders);
        //存储OrderDetail
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        List<Cart> cartList = this.cartMapper.selectList(wrapper);
        for (Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orders.getId());
            BeanUtils.copyProperties(cart, orderDetail);
            this.orderDetailMapper.insert(orderDetail);
        }
        //清空购物车
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("user_id", user.getId());
        this.cartMapper.delete(wrapper1);
        return orders;
    }

    @Override
    public List<OrdersVO> getAllByUserId(Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userId);
        List<Orders> ordersList = this.ordersMapper.selectList(wrapper);
        List<OrdersVO> ordersVOList = new ArrayList<>();
        for (Orders orders : ordersList) {
            OrdersVO ordersVO = new OrdersVO();
            BeanUtils.copyProperties(orders, ordersVO);
            //封装订单详情
            wrapper = new QueryWrapper();
            wrapper.eq("order_id", orders.getId());
            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(wrapper);
            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                BeanUtils.copyProperties(orderDetail, orderDetailVO);
                //查询商品信息
                wrapper = new QueryWrapper();
                wrapper.eq("id", orderDetail.getProductId());
                Product product = this.productMapper.selectOne(wrapper);
                BeanUtils.copyProperties(product, orderDetailVO);
                orderDetailVOList.add(orderDetailVO);
            }
            ordersVO.setOrderDetailVOList(orderDetailVOList);
            ordersVOList.add(ordersVO);
        }
        return ordersVOList;
    }
}
