package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.User;
import com.study.enums.GenderEnum;
import com.study.mapper.UserMapper;
import com.study.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        if(user.getSex() == 0){
            user.setGender(GenderEnum.FEMALE);
        }
        if(user.getSex() == 1){
            user.setGender(GenderEnum.MALE);
        }
        int result = this.userMapper.insert(user);
        if(result == 1)return true;
        return false;
    }

    @Override
    public User login(User user) {
        //验证用户名和密码
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name", user.getLoginName());
        wrapper.eq("password", user.getPassword());
        return this.userMapper.selectOne(wrapper);
    }
}
