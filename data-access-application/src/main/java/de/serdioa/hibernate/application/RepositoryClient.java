package de.serdioa.hibernate.application;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import de.serdioa.hibernate.application.repository.UserRepository;
import de.serdioa.hibernate.domain.QUser;
import de.serdioa.hibernate.domain.User;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public class RepositoryClient {

    private final UserRepository userRepository;


    public RepositoryClient(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void findAll() {
        System.out.println("Reading all Users by query");

        for (User user : this.userRepository.findAll()) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading all Users by query");
    }


    public void findById() {
        System.out.println("Reading all Users by ID");

        for (int id = 0; id < 10; ++id) {
            Optional<User> user = this.userRepository.findById(id);
            if (user.isPresent()) {
                System.out.println("    id=" + id + ": " + user.get());
            } else {
                System.out.println("    id=" + id + ": user not found");
            }
        }

        System.out.println("Finished reading all Users by ID");
    }


    public void findByUsername() {
        System.out.println("Reading users by username");

        System.out.println("Reading user 'Alice'...");
        Optional<User> alice = this.userRepository.findByUsername("Alice");
        System.out.println("    " + (alice.isPresent() ? alice.get().toString() : "not found"));

        System.out.println("Reading user 'Barbara'...");
        Optional<User> barbara = this.userRepository.findByUsername("Barbara");
        System.out.println("    " + (barbara.isPresent() ? barbara.get().toString() : "not found"));

        System.out.println("Finished reading users by username");
    }


    public void findByExpireOnLessThan() {
        System.out.println("Reading users with expired account");

        for (User user : this.userRepository.findByExpireOnLessThan(LocalDate.now())) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading users with expired account");
    }


    public void findLocked() {
        System.out.println("Reading locked users");

        for (User user : this.userRepository.findLocked()) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading locked users");
    }


    public void findSimilarName() {
        System.out.println("Reading users by name pattern");

        for (User user : this.userRepository.findSimilarName("Al%")) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading users by name pattern");
    }


    public void findActiveUsers() {
        System.out.println("Reading active users");

        for (User user : this.userRepository.findActiveUsers()) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading active users");
    }


    public void findActiveUsersCustomQuery() {
        System.out.println("Reading active users with custom QueryDSL query");

        QUser quser = QUser.user;
        Predicate predicate = quser.locked.eq(false)
                .and(quser.passwordChangedOn.gt(ZonedDateTime.now().minusMonths(3)))
                .and(quser.expireOn.isNull().or(quser.expireOn.gt(LocalDate.now())));

        for (User user : this.userRepository.findAll(predicate)) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading active users with custom QueryDSL query");
    }


    public void findActiveUsersRepoQuery() {
        System.out.println("Reading active users with Spring Data repository based on QueryDSL query");

        for (User user : this.userRepository.findActiveUsersQuery()) {
            System.out.println("    " + user);
        }

        System.out.println("Finished reading active users with Spring Data repository based on QueryDSL query");
    }
}
