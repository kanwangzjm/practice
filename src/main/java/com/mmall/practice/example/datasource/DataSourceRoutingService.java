package com.mmall.practice.example.datasource;

import com.mmall.practice.dao.SysUserMapper;
import com.mmall.practice.example.datasource.routing.DataSources;
import com.mmall.practice.example.datasource.routing.RoutingDataSource;
import com.mmall.practice.model.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataSourceRoutingService {

    @Resource
    private SysUserMapper sysUserMapper;

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
}
