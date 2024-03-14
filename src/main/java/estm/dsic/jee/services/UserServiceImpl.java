package estm.dsic.jee.services;

import java.io.Serializable;
import java.util.List;

import estm.dsic.jee.data.dao.UserDao;
import estm.dsic.jee.models.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserServiceImpl implements UserService, Serializable {

    @Inject
    UserDao userDao;

    @Override
    public boolean registerUser(User user) {
        // return userRepositoryImpl.save(user);
        return false;
    }

    @Override
    public User authenticateUser(String email, String password) {
        // Find user by email and password
        // return userRepository.findByEmailAndPassword(email, password);
        return userDao.getUserByEmailAndPassword(email, password);
    }

    // Inside UserServiceImpl class
    @Override
    public List<User> getAllUsers() {
        // return userRepository.getAllUsers();
        // return userRepositoryImpl.findAll();
        return null;

    }

    @Override
    public boolean deleteUserById(int userId) {
        // return userRepository.deleteUserById(userId);
        // return userRepositoryImpl.deleteById(userId);
        return false;
    }

    @Override
    public boolean updateUserById(User user) {
        // return userRepository.updateUserById(user);
        // return userRepositoryImpl.update(user);
        return false;
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        // return userRepository.searchUsersByKeyword(keyword);
        // return userRepositoryImpl.searchByKeyword(keyword);
        return null;
    }

}
