package com.sunixi.pdfgen.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("com.sunixi.pdfgen.app")
@PropertySource("classpath:datasource.properties")
public class DataManagerConfig {

	@Autowired
	Environment env;
	@Bean
	public DataSource dataSource() {
	    Properties dsProps = new Properties();
	    dsProps.put("url", env.getProperty("spring.datasource.url"));
	    dsProps.put("user", env.getProperty("spring.datasource.username"));
	    dsProps.put("password", env.getProperty("spring.datasource.password"));
	    //dsProps.put("prepStmtCacheSize",250);
	    //dsProps.put("prepStmtCacheSqlLimit",2048);
	    //dsProps.put("cachePrepStmts",Boolean.TRUE);
	    //dsProps.put("useServerPrepStmts",Boolean.TRUE);

	    Properties configProps = new Properties();
	       configProps.put("dataSourceClassName", env.getProperty("spring.datasource.database-platform"));
	       configProps.put("poolName",env.getProperty("spring.datasource.poolName"));
	       configProps.put("maximumPoolSize",env.getProperty("spring.datasource.maximumPoolSize"));
	       configProps.put("minimumIdle",env.getProperty("spring.datasource.minimumIdle"));
	       /*configProps.put("maximumLifeTime",env.getProperty("spring.datasource.maxLifetime"));*/
	       configProps.put("connectionTimeout", env.getProperty("spring.datasource.connectionTimeout"));
	       configProps.put("idleTimeout", env.getProperty("spring.datasource.idleTimeout"));
	       configProps.put("dataSourceProperties", dsProps);

	   HikariConfig hc = new HikariConfig(configProps);
	   HikariDataSource ds = new HikariDataSource(hc);
	   return  ds;
	}
	 

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.sunixi.pdfgen");
        emf.setPersistenceProvider(new HibernatePersistenceProvider());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        jpaProperties.setProperty("hibernate.show_sql", "true");
        emf.setJpaProperties(jpaProperties);
        emf.setDataSource(dataSource);
        return emf;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }
}
