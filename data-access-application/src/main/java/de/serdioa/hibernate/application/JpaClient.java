package de.serdioa.hibernate.application;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import de.serdioa.hibernate.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;


@Component
public class JpaClient {

    private final EntityManager em;
    private final TransactionTemplate tt;


    public JpaClient(@Autowired EntityManager em, @Autowired TransactionTemplate tt) {
        this.em = em;
        this.tt = tt;
    }


    public void readAllUsersByQuery() {
        System.out.println("Reading all Users by query");
        TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u", User.class);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });
        System.out.println("Finished reading all Users by query");
    }


    public void readLockedUsersByQuery() {
        System.out.println("Reading locked Users by query");
        TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u WHERE u.locked = :locked", User.class);
        query.setParameter("locked", true);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading locked Users by query");
    }


    public void readActiveUsersByQuery() {
        System.out.println("Reading active Users by query");

        TypedQuery<User> query = this.em.createQuery(
                "SELECT u FROM User u "
                + "WHERE u.locked = false "
                + "AND u.passwordChangedOn > :passwordChangeThreshold "
                + "AND (u.expireOn IS NULL OR u.expireOn > :today)", User.class)
                .setParameter("passwordChangeThreshold", ZonedDateTime.now().minusMonths(3))
                .setParameter("today", LocalDate.now());

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading active Users by query");
    }


    public void readAllUsersByCriteria() {
        System.out.println("Reading all Users by criteria");
        CriteriaQuery<User> criteria = this.em.getCriteriaBuilder().createQuery(User.class);
        criteria.from(User.class);

        List<User> users = this.em.createQuery(criteria).getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });
        System.out.println("Finished reading all Users by criteria");
    }


    public void readLockedUsersByCriteria() {
        System.out.println("Reading locked Users by criteria");

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<Boolean> lockedExpression = cb.parameter(Boolean.class, "locked");
        criteria.select(userRoot).where(cb.equal(userRoot.get("locked"), lockedExpression));

        TypedQuery<User> query = this.em.createQuery(criteria);
        query.setParameter(lockedExpression, true);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });
        System.out.println("Finished reading locked Users by criteria");
    }


    public void readActiveUsersByCriteria() {
        System.out.println("Reading active Users by criteria");

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<Boolean> lockedExpression = cb.parameter(Boolean.class, "locked");

        ParameterExpression<ZonedDateTime> passwordChangedThresholdExpression =
                cb.parameter(ZonedDateTime.class, "passwordChangeThreshold");

        ParameterExpression<LocalDate> expireOnExpression =
                cb.parameter(LocalDate.class, "expireOn");

        Predicate predicate = cb.and(
                cb.equal(userRoot.get("locked"), lockedExpression),
                cb.greaterThan(userRoot.get("passwordChangedOn"), passwordChangedThresholdExpression),
                cb.or(cb.isNull(userRoot.get("expireOn")),
                        cb.greaterThan(userRoot.get("expireOn"), expireOnExpression)));

        TypedQuery<User> query = this.em.createQuery(criteria.select(userRoot).where(predicate))
                .setParameter(lockedExpression, false)
                .setParameter(passwordChangedThresholdExpression, ZonedDateTime.now().minusMonths(3))
                .setParameter(expireOnExpression, LocalDate.now());

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });
        System.out.println("Finished reading active Users by criteria");
    }
}
