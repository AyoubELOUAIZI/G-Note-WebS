package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;
import estm.dsic.jee.data.NoteRepository;
import estm.dsic.jee.data.NoteRepositoryImpl;
import estm.dsic.jee.models.Note;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class NoteServiceImpl implements NoteService, Serializable {

    @Inject
    private NoteRepository noteRepository;
    
    @Inject
    private NoteRepositoryImpl noteRepositoryImpl;

    @Override
    public List<Note> getNotesByOwnerId(int id) {
        return noteRepository.getNotesByOwnerId(id);
    }

    @Override
    public boolean save(Note note) {
        //return noteRepository.saveNote(note);
        return noteRepositoryImpl.save(note);
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
