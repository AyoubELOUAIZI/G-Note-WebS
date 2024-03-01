package estm.dsic.jee.services;

import java.io.Serializable;

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
        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(String email, String password) {
        // Find user by email and password
        return userRepository.findByEmailAndPassword(email, password);
    }

}
