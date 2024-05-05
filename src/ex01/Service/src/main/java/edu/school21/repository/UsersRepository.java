package edu.school21.repository;

import edu.school21.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User>{
    Optional<User> findByEmail(String email);
}
