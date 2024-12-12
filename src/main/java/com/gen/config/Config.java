package com.gen.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.gen.properties.DbProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhanglei_18
 */
@Configuration
@ComponentScan(basePackages = {"com.gen"})
@MapperScan("com.gen.mapper")
@Slf4j
public class Config {

    @Bean
    public DataSource dataSource(DbProperties dbProperties) {

        log.info(dbProperties.toString());

        DruidDataSource ds = new DruidDataSource();
        ds.setUsername(dbProperties.getUserName());
        ds.setPassword(dbProperties.getPassword());
        ds.setDriverClassName(dbProperties.getDriverClassName());
        ds.setUrl(dbProperties.getConnectionUrl());

        List<Object> collection = new LinkedList<>();
        collection.add("set names utf8mb4;");
        ds.setTestWhileIdle(false);
        ds.setConnectionInitSqls(collection);

        try {
            ds.getConnection();
        } catch (Exception e) {
            log.error("", e);
        }
        List<Filter> filters = new ArrayList<>();

        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        filters.add(slf4jLogFilter);

        StatFilter statfilter = new StatFilter();
        statfilter.setMergeSql(true);
        statfilter.setLogSlowSql(true);
        statfilter.setSlowSqlMillis(100);
        filters.add(statfilter);
        ds.setProxyFilters(filters);

        return ds;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryMySQL(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration mybatisConfig = new org.apache.ibatis.session.Configuration();
        mybatisConfig.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(mybatisConfig);
        return sessionFactory.getObject();
    }





}
