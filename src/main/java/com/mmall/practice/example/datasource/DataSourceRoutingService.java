package com.mmall.practice.example.datasource;

import com.mmall.practice.dao.SysUserMapper;
import com.mmall.practice.dao.UserMapper;
import com.mmall.practice.daoLog.TestMapper;
import com.mmall.practice.example.datasource.routing.DataSources;
import com.mmall.practice.example.datasource.routing.RoutingDataSource;
import com.mmall.practice.model.SysUser;
import com.mmall.practice.model.Test;
import com.mmall.practice.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataSourceRoutingService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TestMapper testMapper;
    @Resource
    private UserMapper userMapper;

    @RoutingDataSource(DataSources.MASTER_DB)
    public SysUser list1(int id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @RoutingDataSource(DataSources.SLAVE_DB)
    public SysUser list2(int id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public SysUser list3(int id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    public Test list4(int id) {
        return testMapper.selectByPrimaryKey(id);
    }

    public User list5(int id) {
        User user = new User();
        user.setId(id);
        user.setName("test");
        user.setRemark("remark");
        userMapper.insertSelective(user);
        return user;
    }
}
