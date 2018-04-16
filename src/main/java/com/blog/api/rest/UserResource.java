package com.blog.api.rest;

import com.blog.api.DBIManager;
import com.blog.api.db.UserDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response getUsers(){
        return Response.ok().entity(DBIManager.getJdbi().onDemand(UserDao.class).getUsers()).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response getUser(@PathParam("userId") int userId){
        return Response.ok().entity(DBIManager.getJdbi().onDemand(UserDao.class).getUser(userId)).build();
    }
}