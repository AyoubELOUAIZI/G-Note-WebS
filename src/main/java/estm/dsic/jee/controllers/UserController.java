package estm.dsic.jee.controllers;

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
    @Produces(MediaType.APPLICATION_JSON) // Specify JSON as the response type
    public Response signInUser(User user) {
        System.out.println("\n\nthe function signInUser is called \n" + user);
        // Authenticate the user
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        System.out.println("authenticated user: " + authenticatedUser);

        if (authenticatedUser != null) {
            // Authentication successful
            // SignInResponse response = new SignInResponse(authenticatedUser, "User signed in successfully");
            // System.out.println("response: " + response);
            return Response.ok(authenticatedUser).build();
        } else {
            // Authentication failed
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(ResponseMessages.SIGN_IN_FAILED)
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
    }
}
