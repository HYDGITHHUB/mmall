package com.study.service;

import com.study.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface UserService extends IService<User> {
    public boolean register(User user);
    public User login(User user);
}
