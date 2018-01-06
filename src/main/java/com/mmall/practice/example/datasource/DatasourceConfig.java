package com.mmall.practice.example.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.mmall.practice.example.datasource.routing.DataSources;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @Bean(destroyMethod =  "close", name = DataSources.MASTER_DB)
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(destroyMethod =  "close", name = DataSources.SLAVE_DB)
    @ConfigurationProperties(prefix = "spring.datasourceSlave")
    public DataSource dataSourceSlave() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(destroyMethod =  "close", name = DataSources.LOG_DB)
    @ConfigurationProperties(prefix = "spring.datasourceLog")
    public DataSource dataSourceLog() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

}
