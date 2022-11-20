package de.serdioa.hibernate.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan("de.serdioa.hibernate.domain")
// @EnableJpaRepositories("de.serdioa.dataaccess.repositories")
public class JpaConfiguration {

}