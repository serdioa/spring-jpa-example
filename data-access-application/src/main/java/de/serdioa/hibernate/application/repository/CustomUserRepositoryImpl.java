package de.serdioa.hibernate.application.repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import de.serdioa.hibernate.domain.QUser;
import de.serdioa.hibernate.domain.User;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


public class CustomUserRepositoryImpl implements CustomUserRepository {

    private static final QUser USER = QUser.user;

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory qf;


    @PostConstruct
    public void afterPropertiesSet() {
        System.out.println("em == null ? " + (this.em == null));
        this.qf = new JPAQueryFactory(em);
    }


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


    @Override
    public Iterable<User> findActiveUsersQuery() {
        Predicate predicate = USER.locked.eq(false)
                .and(USER.passwordChangedOn.gt(ZonedDateTime.now().minusMonths(3)))
                .and(USER.expireOn.isNull().or(USER.expireOn.gt(LocalDate.now())));

        return this.qf.select(USER)
                .from(USER)
                .where(predicate)
                .fetch();
    }
}
