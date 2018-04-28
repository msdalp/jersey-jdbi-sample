package com.blog.api;

import com.blog.api.db.AuthUser;
import com.blog.api.db.Token;
import com.blog.api.db.TokenDao;
import com.blog.api.db.UserDao;
import com.blog.api.resp.NoAuth;
import com.blog.api.util.SecurityUser;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
@Priority(Priorities.AUTHORIZATION)
public class AuthFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest webRequest;

    @Context
    HttpServletResponse servletResponse;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final String tokenHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String ws = requestContext.getUriInfo().getPath().split("/")[0];

        if (ws.equals("auth")) {
            return;
        }else if(tokenHeader == null){
            requestContext.abortWith(Response.status(401).entity(new NoAuth()).build());
        }

        final Token token = DBIManager.getJdbi().onDemand(TokenDao.class).getToken(tokenHeader);
        if (token == null) {
            requestContext.abortWith(Response.status(401).entity(new NoAuth()).build());
        } else {
            final AuthUser authUser = DBIManager.getJdbi().onDemand(UserDao.class).getUser(token.getUserId());
            requestContext.setSecurityContext(new SecurityUser(authUser));
        }

    }
}
