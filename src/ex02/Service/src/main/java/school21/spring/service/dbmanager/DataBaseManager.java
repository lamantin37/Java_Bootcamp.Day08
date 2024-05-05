package school21.spring.service.dbmanager;


import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DataBaseManager {
    private static String DB_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_DRIVER;

    private static Connection connection;
    @Getter
    private static HikariDataSource dataSource;

    public DataBaseManager(String url, String username, String password) {
        DB_URL = url;
        DB_USERNAME = username;
        DB_PASSWORD = password;

        try {
            connection = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Fatal error: " + e.getMessage());
        }

    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            System.out.println("Connecting process...");
            dataSource = configureDataSource();
            connection = dataSource.getConnection();
        }
        System.out.println("Connected: " + connection);
        return connection;
    }

    private static HikariDataSource configureDataSource() {
        System.out.println("Configuration...");
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(DB_URL);
        ds.setUsername(DB_USERNAME);
        ds.setPassword(DB_PASSWORD);
        return ds;
    }

}
