package de.serdioa.hibernate.application;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .build().run(args);

        JpaClient jpaClient = ctx.getBean(JpaClient.class);

        jpaClient.readAllUsersByQuery();
        jpaClient.readUnlockedUsersByQuery();

        jpaClient.readAllUsersByCriteria();
        jpaClient.readUnlockedUsersByCriteria();
    }
}
