package com.mmall.practice.example.datasource.multiple;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.mmall.practice.daoLog"}, sqlSessionFactoryRef = "logSqlSessionFactory")
public class MybatisLogConfig {

    @Autowired
    @Qualifier("logDB")
    private DataSource logDB;


    @Bean(name = "logSqlSessionFactory")
    @ConfigurationProperties(prefix = "mybatisLog")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(logDB);
        return sqlSessionFactoryBean;
    }

}
