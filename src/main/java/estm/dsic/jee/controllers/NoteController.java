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
        boolean success = noteService.save(note);
        if (success) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNote(@PathParam("id") int id, Note note) {
        note.setIdNote(id); // Ensure correct ID is set
        boolean success = noteService.update(note);
        if (success) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
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

