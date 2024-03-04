package estm.dsic.jee.controllers;

import java.util.List;

import estm.dsic.jee.models.User;
import estm.dsic.jee.services.UserService;
import estm.dsic.jee.util.ResponseMessages;
import jakarta.inject.Inject;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserController {

    @Inject
    UserService userService;
    
    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signInUser(User user) {
        System.out.println("\n\nthe function signInUser is called \n" + user);
        // Authenticate the user
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        System.out.println("authenticatedUser");
        System.out.println(authenticatedUser);
        if (authenticatedUser != null && authenticatedUser.isSubscribed()) {
            // Authentication successful and user is subscribed
            return Response.ok(authenticatedUser).build();
        } else if (authenticatedUser != null && !authenticatedUser.isSubscribed()) {
            // User is not subscribed
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ResponseMessages.ACCOUNT_NOT_VERIFIED)
                    .build();
        } else {
            // Authentication failed
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ResponseMessages.INCORRECT_CREDENTIALS)
                    .build();
        }
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON) // Specify JSON as the response type
    public Response signUpUser(User user) {
        System.out.println("\n\n\nthe function signUpUser is called in the controller..");
        boolean userSignedUp = userService.registerUser(user);
        if (userSignedUp) {
            // Return a successful response with JSON
            return Response.ok(ResponseMessages.SIGN_UP_SUCCESS).build();
        } else {
            // Return an error response with JSON
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ResponseMessages.SIGN_UP_FAILED)
                    .build();
        }
    }

    @PUT
    @Path("/{userId}/subscribe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response markUserAsSubscribed(@PathParam("userId") int userId) {
        System.out.println("\n\n\nthe subscriber is called....");
        boolean success = userService.markUserAsSubscribed(userId);
        if (success) {
            return Response.ok(ResponseMessages.USER_SUBSCRIBED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ResponseMessages.USER_NOT_SUBSCRIBED)
                    .build();
        }
    }

    class SignInResponse {
        @JsonbProperty("user")
        private User user;

        @JsonbProperty("message")
        private String message;

        // Constructors, getters, and setters
        public SignInResponse() {
        }

        public SignInResponse(User user, String message) {
            this.user = user;
            this.message = message;
        }

        @Override
        public String toString() {
            return "SignInResponse [user=" + user + ", message=" + message + "]";
        }

    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void logoutUser(User user) {
        // Implementation for user logout
        // maybe just remove the http only cokie and set the logout cokie in browser
    }

    // Inside UserController class
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return Response.ok(users).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ResponseMessages.NO_USERS_FOUND)
                    .build();
        }
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUserById(@PathParam("userId") int userId) {
        System.out.println("\n\n\nuser id to delete " + userId);
        boolean success = userService.deleteUserById(userId);
        if (success) {
            return Response.ok(ResponseMessages.USER_DELETED_SUCCESSFULLY).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ResponseMessages.USER_NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserById(User user) {
        boolean success = userService.updateUserById(user);
        if (success) {
            return Response.ok(ResponseMessages.USER_UPDATED_SUCCESSFULLY).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ResponseMessages.USER_UPDATE_FAILED)
                    .build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        boolean success = userService.registerUser(user);
        if (success) {
            return Response.ok(ResponseMessages.USER_ADDED_SUCCESSFULLY).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ResponseMessages.USER_ADD_FAILED)
                    .build();
        }
    }
}
