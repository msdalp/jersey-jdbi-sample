package com.blog.api;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DBIManager.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DBIManager.shutdown();
    }
}
