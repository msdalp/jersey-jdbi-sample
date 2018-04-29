package com.blog.api.util;

import com.blog.api.db.AuthUser;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class SecurityUser implements SecurityContext {

    private final AuthUser user;

    public SecurityUser(AuthUser user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String s) {
        return user.getRole().name().equals(s);
    }

    @Override
    public boolean isSecure() {
        return user != null;
    }

    @Override
    public String getAuthenticationScheme() {
        return "AuthToken";
    }
}
