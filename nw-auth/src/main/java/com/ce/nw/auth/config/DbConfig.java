package com.ce.nw.auth.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author yuit
 * @date time 2018/10/9  15:14
 * 数据库配置
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DbConfig {

    private String username;
    private String url;
    private String  password;
    private String driverClassName;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource dr = new DruidDataSource();
        dr.setUsername(this.username);
        dr.setPassword(this.password);
        dr.setDriverClassName(this.driverClassName);
        dr.setUrl(this.url);

        this.logger.info(this.url+":"+this.username+":"+this.password);

        dr.setInitialSize(10);
        dr.setMinIdle(30);
        dr.setMaxActive(300);
        dr.setMaxWait(3600000);
        dr.setTimeBetweenEvictionRunsMillis(60000);
        dr.setMinEvictableIdleTimeMillis(30000);
        dr.setValidationQuery("select 1 from dual");
        dr.setTestWhileIdle(true);
        dr.setTestOnBorrow(false);
        dr.setTestOnReturn(false);
        dr.setPoolPreparedStatements(false);
        dr.setMaxPoolPreparedStatementPerConnectionSize(20);

        return dr;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

}
