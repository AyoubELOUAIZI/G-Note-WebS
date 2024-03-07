package estm.dsic.jee.controllers;

import java.sql.Date;
import java.util.List;

import estm.dsic.jee.models.User;
import estm.dsic.jee.services.UserService;
import estm.dsic.jee.util.ResponseMessages;
import jakarta.inject.Inject;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import estm.dsic.jee.security.JwtUtil;

import jakarta.ws.rs.core.NewCookie;

@Path("/users")
public class UserController {
    private static final String SECRET_KEY = "my-secret-keyabcd";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    @Inject
    UserService userService;

    // @POST
    // @Path("/signin")
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response signInUser(User user) {
    // System.out.println("\n\nthe function signInUser is called \n" + user);
    // // Authenticate the user
    // User authenticatedUser = userService.authenticateUser(user.getEmail(),
    // user.getPassword());
    // System.out.println("authenticatedUser");
    // System.out.println(authenticatedUser);
    // if (authenticatedUser != null && authenticatedUser.isSubscribed()) {
    // // Authentication successful and user is subscribed
    // return Response.ok(authenticatedUser).build();
    // } else if (authenticatedUser != null && !authenticatedUser.isSubscribed()) {
    // // User is not subscribed
    // return Response.status(Response.Status.UNAUTHORIZED)
    // .entity(ResponseMessages.ACCOUNT_NOT_VERIFIED)
    // .build();
    // } else {
    // // Authentication failed
    // return Response.status(Response.Status.UNAUTHORIZED)
    // .entity(ResponseMessages.INCORRECT_CREDENTIALS)
    // .build();
    // }
    // }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signInUser(User user) {
        System.out.println("\n\n\nthe function signin starta");
        // Authenticate the user
        User authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        if (authenticatedUser != null && authenticatedUser.isSubscribed()) {
            // Generate JWT token
            // String token = JWT.create()
            // .withIssuer("your-issuer")
            // .withSubject(authenticatedUser.getEmail()) // Set email as subject
            // .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            // .sign(Algorithm.HMAC256(SECRET_KEY));
            String token = JWT.create()
                    .withIssuer("http://localhost:9080")
                    .withClaim("userId", authenticatedUser.getId()) // Assuming userId is the ID of the user
                    .withClaim("isAdmin", authenticatedUser.isAdmin()) // Assuming isAdmin is a boolean indicating if
                                                                       // the user is an admin
                    .withClaim("isSubscribed", authenticatedUser.isSubscribed()) // Assuming isSubscribed is a boolean
                                                                                 // indicating if the user is subscribed
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC256(SECRET_KEY));

            // Create a HTTP-only cookie containing the JWT token
            @SuppressWarnings("deprecation")
            NewCookie cookie = new NewCookie("g_note_jwt", token, "/", null, null, -1, true, true);

            System.out.println("\n\n\ncookie");
            System.out.println(cookie);
            // Authentication successful and user is subscribed
            return Response.ok(authenticatedUser)
                    .cookie(cookie)
                    .build();
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
    public Response markUserAsSubscribed(@PathParam("userId") int userId, @CookieParam("g_note_jwt") String jwtToken) {

        // Check if the user is having jwt and is admin
        Response adminResponse = JwtUtil.verifyisAdmin(jwtToken);
        // the condition is updated
        if (adminResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            // If the user is not an admin or there was an issue with authentication, return
            // the response
            System.out.println("the user is not autherized or not admin");
            return adminResponse;
        }

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
    @Path("/signout")
    public Response logoutUser() {
        System.out.println("A user has been requested to log out");

        String token = JWT.create()
                .withIssuer("http://localhost:9080")
                .withExpiresAt(new Date(System.currentTimeMillis()+2000))
                .sign(Algorithm.HMAC256(SECRET_KEY));

        // Create a HTTP-only cookie containing the JWT token
        @SuppressWarnings("deprecation")
        NewCookie cookie = new NewCookie("g_note_jwt", token, "/", null, null, 0, true, true);

        System.out.println("\n\n\ncookie");
        System.out.println(cookie);
        // Authentication successful and user is subscribed
        return Response.ok("removing the cookie").cookie(cookie)
                .build();
    }

    // Inside UserController class
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@CookieParam("g_note_jwt") String jwtToken) {

        // Check if the user is having jwt and is admin
        Response adminResponse = JwtUtil.verifyisAdmin(jwtToken);
        // the condition is updated
        if (adminResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            // If the user is not an admin or there was an issue with authentication, return
            // the response
            System.out.println("the user is not autherized or not admin");
            return adminResponse;
        }

        // after checking the user is have jwt and an admin
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
    public Response deleteUserById(@PathParam("userId") int userId, @CookieParam("g_note_jwt") String jwtToken) {

        // Check if the user is admin
        Response adminResponse = JwtUtil.verifyisAdmin(jwtToken);
        if (adminResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            // If the user is not an admin or there was an issue with authentication, return
            // the response
            return adminResponse;
        }
        System.out.println("the function .............");
        System.out.println("\n\n\nthe user is actually an admin");

        // Proceed with deleting the user
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
    public Response updateUserById(User user, @CookieParam("g_note_jwt") String jwtToken) {

        // Check if the user is having jwt and is admin
        Response adminResponse = JwtUtil.verifyisAdmin(jwtToken);
        // the condition is updated
        if (adminResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            // If the user is not an admin or there was an issue with authentication, return
            // the response
            System.out.println("the user is not autherized or not admin");
            return adminResponse;
        }

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
    public Response addUser(User user, @CookieParam("g_note_jwt") String jwtToken) {

        // Check if the user is having jwt and is admin
        Response adminResponse = JwtUtil.verifyisAdmin(jwtToken);
        // the condition is updated
        if (adminResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            // If the user is not an admin or there was an issue with authentication, return
            // the response
            System.out.println("the user is not autherized or not admin");
            return adminResponse;
        }

        boolean success = userService.registerUser(user);
        if (success) {
            return Response.ok(ResponseMessages.USER_ADDED_SUCCESSFULLY).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ResponseMessages.USER_ADD_FAILED)
                    .build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchUsers(@QueryParam("keyword") String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Keyword parameter is required")
                    .build();
        }

        List<User> users = userService.searchUsersByKeyword(keyword);
        if (!users.isEmpty()) {
            return Response.ok(users).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No users found for the given keyword")
                    .build();
        }
    }
}
