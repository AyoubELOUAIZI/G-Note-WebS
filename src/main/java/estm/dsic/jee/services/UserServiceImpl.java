package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;

import estm.dsic.jee.data.UserRepository;
import estm.dsic.jee.models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserServiceImpl implements UserService, Serializable {

    @Inject
    UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean registerUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public User authenticateUser(String email, String password) {
        // Find user by email and password
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean markUserAsSubscribed(int userId) {
        User user = userRepository.findById(userId);
        System.out.println("\n\n\nin update user found from user services in  markUserAsSubscribed...    " + user);
        if (user != null) {
            user.setSubscribed(true);
            return userRepository.update(user); // Return the result of the update operation
        }
        return false;
    }

    // Inside UserServiceImpl class
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean deleteUserById(int userId) {
        return userRepository.deleteUserById(userId);
    }

    @Override
    public boolean updateUserById(User user) {
        return userRepository.updateUserById(user);
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        return userRepository.searchUsersByKeyword(keyword);
    }

}
