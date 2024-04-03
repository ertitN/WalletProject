package com.example.walletservice.config;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    @Bean(name = "h2DataSource")
    @Primary
    public DataSource h2DataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:mydb");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
/*
    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(){
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url("jdbc:mysql://localhost:3306/ooptest");
            dataSourceBuilder.username("root");
            dataSourceBuilder.password("");
            return dataSourceBuilder.build();
    }

 */
}
