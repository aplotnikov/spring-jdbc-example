package org.home.spring.jdbc.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
@ComponentScan(basePackages = "org.home.spring.jdbc")
public class ApplicationContext {
    @Bean
    @Profile("dev")
    public DataSource aDevDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:db-schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource aProdDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource("jdbc:hsqldb:mem:test", "sa", "");
        driverManagerDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        return driverManagerDataSource;
    }

    @Bean
    public NamedParameterJdbcTemplate aNamedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
