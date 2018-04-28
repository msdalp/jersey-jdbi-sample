package com.blog.api.db;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TokenDao {

    @SqlQuery("SELECT * FROM token;")
    @RegisterBeanMapper(Token.class)
    List<Token> getTokens();

    @SqlQuery("SELECT * FROM token WHERE token=:token and expire_at > now()")
    @RegisterBeanMapper(Token.class)
    Token getToken(@Bind("token") String token);

    @SqlUpdate("INSERT into token(token,user_id,expire_at) values (:token, :userId, :expireAt)")
    @GetGeneratedKeys
    int addToken(@BindBean Token token );
}