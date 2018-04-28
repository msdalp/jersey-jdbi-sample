package com.blog.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;



@ApplicationPath("")
public class RestApp extends ResourceConfig {
    public RestApp() {
        packages("com.blog.api.rest");
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }

    @PostConstruct
    public void postCreate() {
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Inner destroy");
    }
}
