package estm.dsic.jee.data;

import estm.dsic.jee.models.Note;
import java.util.List;

public interface NoteRepository {

    // Retrieve all notes for a specific owner
    List<Note> getNotesByOwnerId(int ownerId);

    // Save a new note
    boolean save(Note note);

    // Update an existing note
    boolean update(Note note);

    // Delete a note by its ID
    boolean delete(int id);

    // New method for searching notes by keyword
    List<Note> searchNotesByKeyword(int userId,String keyword);
}
