package de.serdioa.hibernate.application.repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import de.serdioa.hibernate.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Iterable<User> findActiveUsers() {
        TypedQuery<User> query = this.em.createQuery(
                "SELECT u FROM User u "
                + "WHERE u.locked = false "
                + "AND u.passwordChangedOn > :passwordChangeThreshold "
                + "AND (u.expireOn IS NULL OR u.expireOn > :today)", User.class)
                .setParameter("passwordChangeThreshold", ZonedDateTime.now().minusMonths(3))
                .setParameter("today", LocalDate.now());

        return query.getResultList();
    }
}
