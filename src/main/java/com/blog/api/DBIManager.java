package com.blog.api;

import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

public class DBIManager {
    private static DBIManager instance;

    private final HikariDataSource hikariDataSource;
    private final Jdbi jdbi;

    private DBIManager() throws NamingException {
        this.hikariDataSource = (HikariDataSource) new InitialContext().lookup("java:comp/env/jdbc/hikariSrc");
        this.jdbi = Jdbi.create(hikariDataSource);
        this.jdbi.installPlugin(new SqlObjectPlugin());

    }

    public static DBIManager getInstance() {
        return instance;
    }

    public static synchronized void start() throws ServerErrorException {
        if (instance == null) {
            try {
                instance = new DBIManager();
            } catch (NamingException ex) {
                throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR, ex);
            }
        }
    }

    public static void shutdown() {
        instance.hikariDataSource.close();
    }

    public static Jdbi getJdbi() {
        return instance.jdbi;
    }
}
