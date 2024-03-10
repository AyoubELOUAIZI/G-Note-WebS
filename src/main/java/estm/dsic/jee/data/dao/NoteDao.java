package estm.dsic.jee.data.dao;

import jakarta.enterprise.context.SessionScoped;
// import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import estm.dsic.jee.models.Note;

import java.io.Serializable;
import java.util.List;

@SessionScoped
@Transactional
public class NoteDao implements Serializable {
    @PersistenceContext(unitName = "g_note") // Assuming "g_note" is your persistence unit name
    private EntityManager entityManager;

    public void createNote(Note note) {
        entityManager.persist(note);
    }

    public Note getNoteById(int id) {
        return entityManager.find(Note.class, id);
    }

    public void updateNote(Note note) {
        entityManager.merge(note);
    }

    public void deleteNote(Note note) {
        entityManager.remove(entityManager.contains(note) ? note : entityManager.merge(note));
    }

    public List<Note> getAllNotes() {
        return entityManager.createQuery("SELECT n FROM Note n", Note.class).getResultList();
    }
}