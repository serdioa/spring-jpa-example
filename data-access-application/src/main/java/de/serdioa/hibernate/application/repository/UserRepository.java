package de.serdioa.hibernate.application.repository;

import java.time.LocalDate;
import java.util.Optional;

import de.serdioa.hibernate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


//QuerydslPredicateExecutor<User>, CustomUserRepository
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

    Optional<User> findByUsername(String username);


    Iterable<User> findByExpireOnLessThan(LocalDate expireOn);


    @Query("SELECT u FROM User u WHERE u.locked = true")
    Iterable<User> findLocked();


    @Query("SELECT u FROM User u WHERE u.username like :username")
    Iterable<User> findSimilarName(@Param("username") String username);
}
