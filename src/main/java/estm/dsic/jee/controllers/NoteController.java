package estm.dsic.jee.controllers;

import java.util.List;
import estm.dsic.jee.models.Note;
import estm.dsic.jee.services.NoteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notes")
public class NoteController {

    @Inject
    private NoteService noteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return Response.ok(notes).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteById(@PathParam("id") int id) {
        List<Note> notes = noteService.getNotesByOwnerId(id);
        if (notes != null) {
            return Response.ok(notes).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNote(Note note) {
        try {
            System.out.println("Note to add:\n" + note);

            boolean success = noteService.save(note);
            if (success) {
                return Response.status(Response.Status.CREATED)
                        .entity("Note created successfully")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Failed to create note")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNote(Note note) {
        try {
            // Log the note being updated
            // System.out.println("Note to update:\n" + note);

            // Update the note
            boolean success = noteService.update(note);

            // Check if the update was successful
            if (success) {
                // Return the updated note in the response body
                return Response.ok(note).build();
            } else {
                // Return a 404 Not Found response if the note was not found
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            // Log any exceptions that occur during the update operation
            e.printStackTrace();
            // Return a 500 Internal Server Error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNoteById(@PathParam("id") int id) {
        try {
            boolean success = noteService.delete(id);
            if (success) {
                return Response.ok().entity("Note with id " + id + " deleted successfully").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Note with id " + id + " not found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while processing the request").build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchNotes(@QueryParam("keyword") String keyword, @QueryParam("userId") int userId) {
        System.out.println("the key word is : "+keyword);
        try {
            if (keyword == null || keyword.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Keyword parameter is required")
                        .build();
            }

            List<Note> notes = noteService.searchNotesByKeyword(userId,keyword);
            System.out.println("notes found"+notes);
            if (!notes.isEmpty()) {
                return Response.ok(notes).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No notes found for the given keyword")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while searching notes")
                    .build();
        }
    }

}
