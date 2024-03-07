package estm.dsic.jee.services;

import java.util.List;

import estm.dsic.jee.models.User;

public interface UserService {
  
    boolean registerUser(User user);

    User authenticateUser(String email, String password);

    // Inside UserService interface
    List<User> getAllUsers();

    boolean deleteUserById(int userId);

    boolean updateUserById(User user);

    List<User> searchUsersByKeyword(String keyword);

}
