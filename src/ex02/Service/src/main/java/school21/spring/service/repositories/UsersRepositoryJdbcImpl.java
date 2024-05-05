package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school21.spring.service.dbmanager.DataBaseManager;
import school21.spring.service.models.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("jdbcRepository")
public class UsersRepositoryJdbcImpl implements UsersRepository{
    @Autowired
    private final Connection connection = null;

    @Override
    public Optional<User> findById(Long id) {
        String request = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(new User(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        } catch (SQLException e) {
            throw new RuntimeException("Fatal error: " + e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String request = "SELECT * FROM Users";
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fatal error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User entity) {
        try (Statement statement = connection.createStatement()) {
            int result = statement.executeUpdate("INSERT INTO models.user (id, email) VALUES ("
                    + entity.getIdentifier() + ", '"
                    + entity.getEmail() + "');");

            if (result == 0) {
                System.err.println("User wasn't saved with id: " + entity.getIdentifier());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        String upQuery = "UPDATE Users SET email = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(upQuery)) {
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getIdentifier());
            int result = statement.executeUpdate();

            if (result == 0) {
                System.err.println("User wasn't updated with id: " + entity.getIdentifier());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM Users WHERE id = ";
        try (PreparedStatement statement = connection.prepareStatement(dlQuery + id)) {
            int result = statement.executeUpdate();

            if (result == 0) {
                System.err.println("User not found with id: " + id);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String request = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(new User(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        } catch (SQLException e) {
            throw new RuntimeException("Fatal error: " + e.getMessage());
        }
    }
}
