package com.support.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DruidConfig
 * @Author 吴俊淇
 * @Date 2020/4/17 11:33
 * @Version 1.0
 **/
@Configuration
public class DruidConfig {
    //配置Druid监控
    //1.配置管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin123456");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");//默认允许所有访问
        initParams.put("resetEnable", "false");
//        initParams.put("deny","192.168.3.31");
        bean.setInitParameters(initParams);
        return bean;
    }

    //    2.配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Collections.singletonList("/*"));
        return bean;
    }

    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        // Druid 数据源配置
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/community?useUnicode=true&useSSL=false&serverTimezone=Hongkong&characterEncoding=utf-8&autoReconnect=true");
        dataSource.setUsername("root");
        dataSource.setPassword("sw3377549");
        // 初始连接数(默认值0)
        dataSource.setInitialSize(8);
        // 最小连接数(默认值0)
        dataSource.setMinIdle(8);
        dataSource.setMaxActive(32);
        // 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降， 如果需要可以通过配置useUnfairLock属性为true使用非公平锁
        dataSource.setMaxWait(60000);
        dataSource.setUseUnfairLock(true);
//        Destroy线程会检测连接的间隔时间，单位是毫秒 testWhileIdle的判断依据
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
//        连接不管是否空闲,存活phyTimeoutMillis后强制回收，用于Destroy线程清理连接的时候的检测时间
        dataSource.setPhyTimeoutMillis(120000);
//        连接的最小生存的时间，单位是毫秒， Destory线程中如果检测到当前连接的最后活跃时间和当前时间的差值大于 minEvictableIdleTimeMillis，则关闭当前连接。
        dataSource.setMinEvictableIdleTimeMillis(200000);
//        连接的最大存活时间，如果连接的最大时间大于maxEvictableIdleTimeMillis，则无视最小连接数强制回收
        dataSource.setMaxEvictableIdleTimeMillis(300000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);//
        dataSource.setFilters("stat,wall,log4j");
        dataSource.setUseGlobalDataSourceStat(true);
        dataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500");
        return dataSource;
    }

}
