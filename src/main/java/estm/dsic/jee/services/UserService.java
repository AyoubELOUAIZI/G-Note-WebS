package estm.dsic.jee.services;

import java.util.List;

import estm.dsic.jee.models.User;

public interface UserService {
    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    boolean registerUser(User user);

    User authenticateUser(String email, String password);

    boolean markUserAsSubscribed(int userId);

    // Inside UserService interface
    List<User> getAllUsers();

    boolean deleteUserById(int userId);

    boolean updateUserById(User user);

}
