package com.blog.api.db;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface UserDao{

    @SqlQuery("SELECT * FROM user;")
    @RegisterBeanMapper(User.class)
    List<User> getUsers();

    @SqlQuery("SELECT * FROM user WHERE id=:id")
    @RegisterBeanMapper(User.class)
    User getUser(@Bind("id") int id );

}