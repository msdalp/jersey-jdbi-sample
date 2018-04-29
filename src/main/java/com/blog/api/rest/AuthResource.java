package com.blog.api.rest;

import com.blog.api.DBIManager;
import com.blog.api.db.AuthUser;
import com.blog.api.db.Token;
import com.blog.api.db.TokenDao;
import com.blog.api.db.UserDao;
import com.blog.api.resp.Responsive;
import org.mindrot.jbcrypt.BCrypt;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@Path("auth")
public class AuthResource {
    private static final Random secureRandom = new SecureRandom();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    // we can set consume type in here as form url directly
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    public Response login(AuthUser userLogin) {

        if (!userLogin.isValid()) {
            return Responsive.loginFailed();
        }

        final AuthUser user = DBIManager.getJdbi().onDemand(UserDao.class).getUserByName(userLogin.getUsername());

        // make sure user or password is available
        // check if password matches as well
        // otherwise throw not authorized
        if (user == null || !BCrypt.checkpw(userLogin.getPassword(), user.getPassword())) {
            return Responsive.loginFailed();
        }

        final Token token = new Token();
        token.setUserId(user.getId());
        // you can use any kind of token generation method
        token.setToken(new BigInteger(130, secureRandom).toString(32));
        // expire after one day
        token.setExpireAt(Timestamp.from(Instant.now().plus(1,ChronoUnit.DAYS)));
        // get the inserted id
        token.setId(DBIManager.getJdbi().onDemand(TokenDao.class).addToken(token));
        return Responsive.created(token);
    }

}
