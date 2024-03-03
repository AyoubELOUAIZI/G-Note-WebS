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
         System.out.println("Note to ADD:\n" + note);
        boolean success = noteService.save(note);
        if (success) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
        boolean success = noteService.delete(id);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
