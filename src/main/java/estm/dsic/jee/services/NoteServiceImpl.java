package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;

import estm.dsic.jee.data.dao.NoteDao;
import estm.dsic.jee.models.Note;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class NoteServiceImpl implements NoteService, Serializable {

    @Inject
    NoteDao noteDao;

    @Override
    public List<Note> getNotesByOwnerId(int id) {
        return noteDao.getAllNotes();
    }

    @Override
    public boolean save(Note note) {
      
        return noteDao.createNote(note);
    }

    @Override
    public boolean update(Note note) {
        // return noteRepository.update(note);
        return false;
    }

    @Override
    public boolean delete(int id) {
        // return noteRepository.delete(id);
        return false;
    }

    @Override
    public List<Note> searchNotesByKeyword(int userId, String keyword) {
        // return noteRepository.searchNotesByKeyword(userId,keyword);
        return null;
    }
}
