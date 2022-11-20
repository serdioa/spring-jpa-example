package de.serdioa.hibernate.application.repository;

import de.serdioa.hibernate.domain.User;


public interface CustomUserRepository {
    Iterable<User> findActiveUsers();
}
