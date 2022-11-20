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
        testJpaClient(jpaClient);

        RepositoryClient repoClient = ctx.getBean(RepositoryClient.class);
        testRepositoryClient(repoClient);
    }


    private static void testJpaClient(JpaClient jpaClient) {
        jpaClient.readAllUsersByQuery();
        jpaClient.readUnlockedUsersByQuery();
        jpaClient.readAccountExpiredUsersByQuery();
        jpaClient.readPasswordExpiredUsersByQuery();

        jpaClient.readAllUsersByCriteria();
        jpaClient.readUnlockedUsersByCriteria();
        jpaClient.readAccountExpiredUsersByCriteria();
        jpaClient.readPasswordExpiredUsersByCriteria();
    }


    private static void testRepositoryClient(RepositoryClient repoClient) {
        repoClient.findAll();
        repoClient.findById();
        repoClient.findByUsername();
        repoClient.findByExpireOnLessThan();
        repoClient.findLocked();
        repoClient.findSimilarName();

        repoClient.findActiveUsers();
    }
}
