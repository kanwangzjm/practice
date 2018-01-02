package com.mmall.practice;

import com.alibaba.druid.pool.DruidDataSource;
import com.mmall.practice.example.datasource.routing.DynamicDataSource;
import com.mmall.practice.example.elastic.EmbedZookeeperServer;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableHystrixDashboard
@EnableCircuitBreaker
@MapperScan(basePackages = {"com.mmall.practice.dao"})
public class PracticeApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        EmbedZookeeperServer.start(6181);
        SpringApplication.run(PracticeApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpFilter());
        registration.addUrlPatterns("/threadLocal/*");
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");
    }

//    @Autowired
//    private Environment env;

    //destroy-method="close"的作用是当数据库连接不使用的时候,就把该连接重新放到数据池中,方便下次使用调用.
    @Bean(destroyMethod =  "close", name = "masterDB")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setInitialSize(2);//初始化时建立物理连接的个数
//        dataSource.setMaxActive(20);//最大连接池数量
//        dataSource.setMinIdle(0);//最小连接池数量
//        dataSource.setMaxWait(60000);//获取连接时最大等待时间，单位毫秒。
//        dataSource.setValidationQuery("SELECT 1");//用来检测连接是否有效的sql
//        dataSource.setTestOnBorrow(false);//申请连接时执行validationQuery检测连接是否有效
//        dataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。
//        dataSource.setPoolPreparedStatements(false);//是否缓存preparedStatement，也就是PSCache
//        return dataSource;
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(destroyMethod =  "close", name = "slaveDB")
    @ConfigurationProperties(prefix = "spring.datasourceSlave")
    public DataSource dataSourceSlave() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(destroyMethod =  "close", name = "logDB")
    @ConfigurationProperties(prefix = "spring.datasourceLog")
    public DataSource dataSourceLog() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("masterDB", dataSource());
        dsMap.put("slaveDB", dataSourceSlave());
        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
}
