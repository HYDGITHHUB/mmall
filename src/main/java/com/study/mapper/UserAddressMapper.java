package com.study.mapper;

import com.study.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    public int clearDefault(Integer userId);
}
