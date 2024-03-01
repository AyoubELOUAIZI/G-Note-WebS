package estm.dsic.jee.data;

import estm.dsic.jee.models.User;

public interface UserRepository {
    User findById(int id);
    User findByUsername(String username);
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    boolean save(User user);
    boolean update(User user);
    void delete(User user);
}
