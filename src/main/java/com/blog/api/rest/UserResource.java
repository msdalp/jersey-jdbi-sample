package com.blog.api.rest;

import com.blog.api.DBIManager;
import com.blog.api.db.AuthUser;
import com.blog.api.db.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

@RolesAllowed({"admin", "mod"})
@Path("users")
public class UserResource {

    private final UserDao dao = DBIManager.getJdbi().onDemand(UserDao.class);

    @RolesAllowed("user")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response getUsers() {
        return Response.ok().entity(dao.getUsers()).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response getUser(@PathParam("userId") int userId) {
        return Response.ok().entity(dao.getUser(userId)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    // we can set consume type in here as json directly
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("")
    // Jackson will convert the posted json to model directly.
    // you can just consume the object.
    public Response addUser(AuthUser user) {
        // generate a new salt for user with 10 rounds
        final String userSalt = BCrypt.gensalt(10, new SecureRandom());
        // TODO validate password
        // hash the password with the salt created above.
        // I just assumed password is proper but you should have
        // password validations before doing any process
        user.setPassword(BCrypt.hashpw(user.getPassword(), userSalt));
        // addUser method will return the created user id and
        // we set that id to user body
        user.setId(dao.addUser(user));
        // return the user with HTTP 201 status
        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response delUser(@PathParam("userId") int userId) {
        dao.deleteUser(userId);
        // return no content 204 on delete. there is no
        // point to return any other message and transfer data
        return Response.noContent().build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response updateUser(@PathParam("userId") int userId, AuthUser user) {
        // set the user id so we can just use user bean on jdbi
        user.setId(userId);
        // update user with jdbi method
        dao.updateUser(user);
        // return HTTP 201 with the body
        return Response.status(Response.Status.CREATED).entity(user).build();
    }
}