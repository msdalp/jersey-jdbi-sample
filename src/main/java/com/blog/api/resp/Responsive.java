package com.blog.api.resp;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class Responsive {

    public static <T> Response ofData(T t) {
        if (t instanceof List && ((List) t).isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(ErrorMessage.notFound()).build();
        } else if (t == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ErrorMessage.notFound()).build();
        }

        return Response.status(200).entity(t).build();
    }

    public static <T> Response created(T t) {
        if (t instanceof List && ((List) t).isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.CREATED).entity(t).build();
    }

    public static Response loginFailed() {
        return Response.status(401).entity(ErrorMessage.loginFailed()).build();
    }

    public static Response noAuth() {
        return Response.status(401).entity(ErrorMessage.noAuth()).build();
    }

    public static void noAuthAbort(final ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(401).type(MediaType.APPLICATION_JSON).entity(ErrorMessage.noAuth()).build());
    }
}
