package estm.dsic.jee.services;

import java.util.List;
import estm.dsic.jee.models.Note;

public interface NoteService {

    // Retrieve all notes
    List<Note> getAllNotes();

    // Retrieve a note by its ID
    Note getNoteById(int id);

    // Save a new note
    boolean save(Note note);

    // Update an existing note
    boolean update(Note note);

    // Delete a note by its ID
    boolean delete(int id);
}
