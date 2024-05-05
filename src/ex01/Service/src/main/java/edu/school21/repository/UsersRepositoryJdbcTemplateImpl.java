package edu.school21.repository;

import edu.school21.dbmanager.DataBaseManager;
import edu.school21.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate
            = new NamedParameterJdbcTemplate(DataBaseManager.getDataSource());

    @Override
    public Optional<User> findById(Long id) {
        String request = "SELECT * FROM Users WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(
                    request,
                    parameters,
                    (ResultSet rs, int rowNum)
                            -> new User(rs.getLong("id"), rs.getString("email")));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String request = "SELECT * FROM Users";
        try {
            users = namedParameterJdbcTemplate.query(
                    request,
                    (ResultSet rs, int rowNum)
                            -> new User(rs.getLong("id"), rs.getString("email")));
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void save(User entity) {
        String inQuery = "INSERT INTO Users (id, email) VALUES (:id, :email)";
        if (namedParameterJdbcTemplate.update(inQuery, new MapSqlParameterSource()
                .addValue("id", entity.getIdentifier())
                .addValue("email", entity.getEmail())) == 0)
        {
            System.err.println("User wasn't saved with id: " + entity.getIdentifier());
        }
    }

    @Override
    public void update(User entity) {
        String upQuery = "UPDATE Users SET email = :email WHERE id = :id";
        if (namedParameterJdbcTemplate.update(upQuery, new MapSqlParameterSource()
                .addValue("id", entity.getIdentifier())
                .addValue("email", entity.getEmail())) == 0)
        {
            System.err.println("User wasn't updated with id: " + entity.getIdentifier());
        }
    }

    @Override
    public void delete(Long id) {
        String dlQuery = "DELETE FROM Users WHERE id = :id";
        if (namedParameterJdbcTemplate.update(dlQuery, new MapSqlParameterSource()
                .addValue("id", id)) == 0)
        {
            System.err.println("User not found with id: " + id);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String request = "SELECT * FROM Users WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", email);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(
                    request,
                    parameters,
                    (ResultSet rs, int rowNum)
                            -> new User(rs.getLong("id"), rs.getString("email")));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }
}
