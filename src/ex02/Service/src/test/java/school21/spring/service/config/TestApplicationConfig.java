package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;
import school21.spring.service.dbmanager.DataBaseManager;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
@Import(school21.spring.service.config.ApplicationConfig.class)
public class TestApplicationConfig {
    @Bean
    @Scope("singleton")
    DataSource dataSource() {
        return configureDataSource();
    }

    private static HikariDataSource configureDataSource() {
        System.out.println("[Test] Configuration...");
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean("usersServiceJdbcTemplate")
    UsersService usersServiceJdbcTemplate(DataSource dataSource) {
        return new UsersServiceImpl();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean("jdbcTemplateRepository")
    public UsersRepositoryJdbcTemplateImpl usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl();
    }
}