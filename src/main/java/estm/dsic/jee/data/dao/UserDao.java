package estm.dsic.jee.data.dao;

import jakarta.enterprise.context.SessionScoped;
// import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
// import estm.dsic.jee.data.NoteRepository;
import estm.dsic.jee.models.User;

import java.io.Serializable;
import java.util.List;

@Transactional
@SessionScoped
public class UserDao implements Serializable {

    @PersistenceContext(unitName = "g_note") // Assuming "g_note" is your persistence unit name
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }

    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User getUserByEmailAndPassword(String email, String password) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // User not found
        }
    }
}
