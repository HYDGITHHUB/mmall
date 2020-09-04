package com.study.service.impl;

import com.study.entity.UserAddress;
import com.study.mapper.UserAddressMapper;
import com.study.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

}
