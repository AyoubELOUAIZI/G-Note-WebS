package estm.dsic.jee.services;

import estm.dsic.jee.models.User;

public interface UserService {
    User getUserById(int id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean registerUser(User user);
    User authenticateUser(String email, String password);
    boolean markUserAsSubscribed(int userId);
}

