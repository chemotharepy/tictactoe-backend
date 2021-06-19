package com.hellochemo.dbconfig;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // equivalent to <tx:annotation-driven>
public class DbConfig {

    // reading value from properties file and giving to the datasource
    @Value("com.mysql.cj.jdbc.Driver")
    private String driverName;

    @Value("jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3419571")
    private String url;

    @Value("sql3419571")
    private String userName;

    @Value("tPxV6x4UgM")
    private String password;
    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //Data source can have any name
    @Bean(name = "cst_DataSource")
    public DriverManagerDataSource getDataSource() {
        System.out.println("Connection Established");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    public HibernateJpaVendorAdapter getVendorAdaptor(){
        HibernateJpaVendorAdapter adapter = new  HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return adapter;
    }


    @Bean(name = "cst_entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getSessionFactory(DriverManagerDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBuilder = new LocalContainerEntityManagerFactoryBean();
        factoryBuilder.setDataSource(dataSource);
        factoryBuilder.setJpaVendorAdapter(getVendorAdaptor());
        factoryBuilder.setPackagesToScan("com.hellochemo.entity");
        return factoryBuilder;
    }
    @Bean(name = "txManager")
    public JpaTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
        return transactionManager;
    }


}


