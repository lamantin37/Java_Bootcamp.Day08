package school21.spring.service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import java.util.Optional;
import java.util.UUID;
@Component("UserService")
public class UsersServiceImpl implements UsersService{
    private Long id = 0L;
    private UsersRepositoryJdbcTemplateImpl usersRepository;

    @Autowired
    public void UsersRepository(@Qualifier("jdbcTemplateRepository") UsersRepositoryJdbcTemplateImpl usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Override
    public String SignUp(String email) {
        UUID password = UUID.randomUUID();

        System.out.println("Email: " + email);

        if (email == null || email.isEmpty()) {
            System.err.println("Error: email not specified");
            return null;
        }

        Optional<User> entry = usersRepository.findByEmail(email);
        if (entry.isPresent()) {
            return entry.get().getPassword();
        } else {
            usersRepository.save(new User(++id, email, password.toString()));
            return password.toString();
        }
    }
}
