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


    public void readUnlockedUsersByQuery() {
        System.out.println("Reading unlocked Users by query");
        TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u WHERE u.locked = :locked", User.class);
        query.setParameter("locked", false);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading unlocked Users by query");
    }


    public void readAccountExpiredUsersByQuery() {
        System.out.println("Reading Users with expired account by query");
        TypedQuery<User> query = this.em.createQuery("SELECT u FROM User u WHERE u.expireOn < :expireOn", User.class);
        query.setParameter("expireOn", LocalDate.now());

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading Users with expired account by query");
    }


    public void readPasswordExpiredUsersByQuery() {
        System.out.println("Reading Users with expired password by query");

        ZonedDateTime thresholdTimestamp = ZonedDateTime.now().minusMonths(3);

        TypedQuery<User> query = this.em
                .createQuery("SELECT u FROM User u WHERE u.passwordChangedOn < :thresholdTimestamp", User.class);
        query.setParameter("thresholdTimestamp", thresholdTimestamp);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading Users with expired account by query");
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


    public void readUnlockedUsersByCriteria() {
        System.out.println("Reading unlocked Users by criteria");

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<Boolean> lockedExpression = cb.parameter(Boolean.class, "locked");
        criteria.select(userRoot).where(cb.equal(userRoot.get("locked"), lockedExpression));

        TypedQuery<User> query = this.em.createQuery(criteria);
        query.setParameter(lockedExpression, false);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });
        System.out.println("Finished reading unlocked Users by criteria");
    }


    public void readAccountExpiredUsersByCriteria() {
        System.out.println("Reading Users with expired account by criteria");

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<LocalDate> expireOnExpression = cb.parameter(LocalDate.class, "expireOn");
        criteria.select(userRoot).where(cb.lessThan(userRoot.get("expireOn"), expireOnExpression));

        TypedQuery<User> query = this.em.createQuery(criteria);
        query.setParameter(expireOnExpression, LocalDate.now());

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading Users with expired account by criteria");
    }


    public void readPasswordExpiredUsersByCriteria() {
        System.out.println("Reading Users with expired password by criteria");

        ZonedDateTime thresholdTimestamp = ZonedDateTime.now().minusMonths(3);

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class);

        ParameterExpression<ZonedDateTime> passwordChangedOnExpression =
                cb.parameter(ZonedDateTime.class, "thresholdTimestamp");
        criteria.select(userRoot).where(cb.lessThan(userRoot.get("passwordChangedOn"), passwordChangedOnExpression));

        TypedQuery<User> query = this.em.createQuery(criteria);
        query.setParameter(passwordChangedOnExpression, thresholdTimestamp);

        List<User> users = query.getResultList();

        users.forEach(user -> {
            System.out.printf("    %s%n", user);
        });

        System.out.println("Finished reading Users with expired account by criteria");
    }
}
