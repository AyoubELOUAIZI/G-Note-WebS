package estm.dsic.jee.data;

import java.util.List;

import estm.dsic.jee.models.User;

public interface UserRepository {
    User findById(int id);

    User findByUsername(String username);

    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    boolean update(User user);

    void delete(User user);

    // Inside UserRepository interface
    List<User> getAllUsers();

    boolean deleteUserById(int userId);

    boolean updateUserById(User user);

    boolean addUser(User user);

}
