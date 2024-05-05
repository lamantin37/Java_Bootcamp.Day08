package edu.school21.app;

import edu.school21.repository.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("UsersRepositoryJdbc", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("UsersRepositoryJdbcTemplate", UsersRepository.class);
        System.out.println(usersRepository.findAll());
    }
}
