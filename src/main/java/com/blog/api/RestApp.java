package com.blog.api;

import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("")
public class RestApp extends ResourceConfig {
    public RestApp(){
        packages("com.blog.api.rest");
    }

    @PostConstruct
    public void postCreate(){
        DBIManager.getJdbi().withHandle(handle -> {
            handle.execute("CREATE TABLE IF NOT EXISTS `user` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n");
            handle.execute("INSERT INTO user (name) VALUES (?)",  "Alice");
            handle.execute("INSERT INTO user (name) VALUES (?)",  "John");
            handle.execute("INSERT INTO user (name) VALUES (?)",  "Msd");
            return null;
        });
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("Inner destroy");
    }
}
