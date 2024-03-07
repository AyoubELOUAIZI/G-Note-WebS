package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;
import estm.dsic.jee.data.NoteRepository;
import estm.dsic.jee.models.Note;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class NoteServiceImpl implements NoteService, Serializable {

    @Inject
    private NoteRepository noteRepository;

    @Override
    public List<Note> getNotesByOwnerId(int id) {
        return noteRepository.getNotesByOwnerId(id);
    }

    @Override
    public boolean save(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public boolean update(Note note) {
        return noteRepository.update(note);
    }

    @Override
    public boolean delete(int id) {
        return noteRepository.delete(id);
    }

    @Override
    public List<Note> searchNotesByKeyword(int userId,String keyword) {
        return noteRepository.searchNotesByKeyword(userId,keyword);
    }
}
