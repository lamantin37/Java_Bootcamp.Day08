package school21.spring.service.services;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersServiceImplTest {
    private static DataSource dataSource;
    private static UsersService usersServiceJdbcTemplate;

    @BeforeAll
    static void before() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
    }

    @BeforeEach
    public void init() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS Users;");
            st.executeUpdate("CREATE TABLE Users(ID INT,\n email VARCHAR(255), password VARCHAR(255));");
            st.executeUpdate("INSERT INTO USERS VALUES(1, 'user3@school21.ru', '123');");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @AfterAll
    public static void clean() {
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS Users;");
            st.executeUpdate("CREATE TABLE Users(ID INT,\n email VARCHAR(255), password VARCHAR(255));");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"user1@school21.ru", "user2@school21.ru", "user3@school21.ru"})
    public void isSignedUpTemplate(String email) {
        assertNotNull(usersServiceJdbcTemplate.SignUp(email));
    }
}