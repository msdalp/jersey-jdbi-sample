package com.blog.api.db;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface UserDao{

    @SqlQuery("SELECT * FROM auth_user")
    @RegisterBeanMapper(AuthUser.class)
    List<AuthUser> getUsers();

    @SqlQuery("SELECT * FROM auth_user WHERE id=:id")
    @RegisterBeanMapper(AuthUser.class)
    AuthUser getUser(@Bind("id") int id );

    @SqlQuery("SELECT * FROM auth_user WHERE username=:username")
    @RegisterBeanMapper(AuthUser.class)
    AuthUser getUserByName(@Bind("username")  String username);

    @SqlUpdate("INSERT into auth_user(username, password, role) values (:username, :password, :role)")
    @GetGeneratedKeys
    int addUser(@BindBean AuthUser user);

    @SqlUpdate("UPDATE auth_user set username=:username, password=:password, role=:role where id=:id")
    void updateUser(@BindBean AuthUser user);

    @SqlUpdate("DELETE from auth_user where id=:id")
    void deleteUser(@Bind("id") int id);
}