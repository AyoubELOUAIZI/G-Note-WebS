package estm.dsic.jee.security;

import java.io.IOException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ws.rs.core.Response;

public class JwtUtil {
    private static final String SECRET_KEY = "my-secret-keyabcd";

    // Method to validate JWT token
    public static DecodedJWT validateJWT(String jwtToken) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(jwtToken);
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    // public static Response verifyisAdmin(String jwtToken) {
    // System.out.println("\n\n\n\njwtToken");
    // System.out.println(jwtToken);
    // // Validate JWT token
    // DecodedJWT decodedJWT = validateJWT(jwtToken);

    // System.out.println("decodedJWT");
    // System.out.println(decodedJWT);

    // if (decodedJWT == null) {
    // System.out.println("decodedJWT is null :"+decodedJWT);
    // // Return an unauthorized response if the token is invalid
    // return Response.status(Response.Status.UNAUTHORIZED)
    // .entity("Invalid JWT token or missing token g_note_jwt")
    // .build();
    // }

    // // Check if the 'isAdmin' claim exists and is true
    // if (decodedJWT.getClaim("isAdmin") != null) {
    // boolean isAdmin = decodedJWT.getClaim("isAdmin").asBoolean();
    // if (!isAdmin) {
    // // Return a forbidden response if the user is not an admin
    // return Response.status(Response.Status.FORBIDDEN)
    // .entity("You are not allowed to perform this action because you are not an
    // admin.")
    // .build();
    // }
    // // Return an OK response if the user is an admin
    // return Response.ok().build();
    // }
    // // Default to an unauthorized response if the claim doesn't exist or is not a
    // boolean
    // return Response.status(Response.Status.UNAUTHORIZED)
    // .entity("Unauthorized")
    // .build();
    // }

    public static Response verifyisAdmin(String jwtToken) {
        try {
            System.out.println("\n\n\n\njwtToken");
            System.out.println(jwtToken);

            // Validate JWT token
            DecodedJWT decodedJWT = validateJWT(jwtToken);

            System.out.println("decodedJWT");
            System.out.println(decodedJWT);

            if (decodedJWT == null) {
                System.out.println("decodedJWT is null :" + decodedJWT);
                // Return an unauthorized response if the token is invalid
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid JWT token or missing token g_note_jwt")
                        .build();
            }
            System.out.println("now will check is admin................");

            // Check if the 'isAdmin' claim exists and is true
            if (decodedJWT.getClaim("isAdmin") != null) {
                boolean isAdmin = decodedJWT.getClaim("isAdmin").asBoolean();
                if (!isAdmin) {
                    // Return a forbidden response if the user is not an admin
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("You are not allowed to perform this action because you are not an admin.")
                            .build();
                }
                // Return an OK response if the user is an admin
                System.out.println("Ok response");
                return Response.ok().build();
            }
            // Default to an unauthorized response if the claim doesn't exist or is not a
            // boolean
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized")
                    .build();
        } catch (Exception e) {
            // Handle any unexpected exceptions
            e.printStackTrace(); // Log the exception for debugging purposes
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred")
                    .build();
        }
    }

    public static Response verifyisSubscribed(String jwtToken) {
        try {
            System.out.println("\n\n\n\njwtToken");
            System.out.println(jwtToken);

            // Validate JWT token
            DecodedJWT decodedJWT = validateJWT(jwtToken);

            System.out.println("decodedJWT");
            System.out.println(decodedJWT);

            if (decodedJWT == null) {
                System.out.println("decodedJWT is null :" + decodedJWT);
                // Return an unauthorized response if the token is invalid
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid JWT token or missing token g_note_jwt")
                        .build();
            }
            System.out.println("now will check is subscribed................");

            // Check if the 'isSubscribed' claim exists and is true
            if (decodedJWT.getClaim("isSubscribed") != null) {
                boolean isSubscribed = decodedJWT.getClaim("isSubscribed").asBoolean();
                if (!isSubscribed) {
                    // Return a forbidden response if the user is not an admin
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity("You are not allowed to perform this action because you are not an subscribed/Verifyed yet.")
                            .build();
                }
                // Return an OK response if the user is an admin
                System.out.println("Ok response for subscription");
                return Response.ok().build();
            }
            // Default to an unauthorized response if the claim doesn't exist or is not a
            // boolean
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized")
                    .build();
        } catch (Exception e) {
            // Handle any unexpected exceptions
            e.printStackTrace(); // Log the exception for debugging purposes
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred when verifying the subscription")
                    .build();
        }
    }

}
