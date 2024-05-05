package school21.spring.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import school21.spring.service.dbmanager.DataBaseManager;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ComponentScan
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String username;

    @Value("${db.password}")
    private String password;
    @Bean
    @Scope("singleton")
    public DataBaseManager dataBaseManager() {
        return new DataBaseManager(url, username, password);
    }

    @Bean
    public Connection connection() throws SQLException {
        return DataBaseManager.getConnection();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(DataBaseManager.getDataSource());
    }
}
