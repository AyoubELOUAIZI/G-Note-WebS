package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;

import estm.dsic.jee.data.UserRepository;
import estm.dsic.jee.data.UserRepositoryImpl;
import estm.dsic.jee.models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserServiceImpl implements UserService, Serializable {

    @Inject
    UserRepository userRepository;

    @Inject
    UserRepositoryImpl userRepositoryImpl;

    @Override
    public boolean registerUser(User user) {
        return userRepositoryImpl.save(user);
    }

    @Override
    public User authenticateUser(String email, String password) {
        // Find user by email and password
        return userRepository.findByEmailAndPassword(email, password);
    }

    // Inside UserServiceImpl class
    @Override
    public List<User> getAllUsers() {
      //  return userRepository.getAllUsers();
        return userRepositoryImpl.findAll();

    }

    @Override
    public boolean deleteUserById(int userId) {
       // return userRepository.deleteUserById(userId);
        return userRepositoryImpl.deleteById(userId);
    }

    @Override
    public boolean updateUserById(User user) {
       // return userRepository.updateUserById(user);
        return userRepositoryImpl.update(user);
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        //return userRepository.searchUsersByKeyword(keyword);
        return userRepositoryImpl.searchByKeyword(keyword);
    }

}
