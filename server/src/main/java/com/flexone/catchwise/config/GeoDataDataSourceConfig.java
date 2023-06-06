package com.flexone.catchwise.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

//@Configuration
@EnableJpaRepositories(basePackages = "com.flexone.catchwise.repositories.geodata",
        entityManagerFactoryRef = "geoDataEntityManagerFactory",
        transactionManagerRef = "geoDataTransactionManager")
public class GeoDataDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.geodata")
    public DataSourceProperties geoDataDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.geodata.configuration")
    public DataSource geoDataDataSource() {
        return geoDataDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "geoDataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean geoDataEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(geoDataDataSource())
                .packages("com.flexone.catchwise.domain.geodata")
                .build();
    }

    @Bean
    public PlatformTransactionManager geoDataTransactionManager(
            final @Qualifier("geoDataEntityManagerFactory") LocalContainerEntityManagerFactoryBean geoDataEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(geoDataEntityManagerFactory.getObject()));
    }


}
