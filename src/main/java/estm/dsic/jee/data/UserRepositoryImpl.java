package estm.dsic.jee.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.ResultSet;

import estm.dsic.jee.data.gen.Repository;
import estm.dsic.jee.models.User;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserRepositoryImpl implements UserRepository, Serializable, Repository<User, Integer> {

    @Resource(lookup = "jdbc/g_note")
    private DataSource dataSource;

    private static final String TABLE_NAME = "user";

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    // Populate other user fields if needed
                    System.out.println("\n\n\nuser in the repository \n" + user);
                    return user;
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            e.printStackTrace();
        }
        return null; // Return null if user not found or an exception occurred
    }

    @Override
    public boolean update(User user) {
        System.out.println("\n\n\nuser update" + user);
        // Implementation to update user information in the database
        String sql = "UPDATE " + TABLE_NAME + " SET isSubscribed = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, user.isSubscribed());
            statement.setInt(2, user.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            return false; // Return false if an exception occurred
        }
    }

    @Override
    public void delete(User user) {
        // Implementation to delete a user from the database
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    user.setCreatedAt(resultSet.getTimestamp("createdAt"));

                    // Populate other user fields if needed
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
        }
        System.out.println("list of users");
        System.out.println(users);
        return users;
    }

    @Override
    public boolean deleteUserById(int userId) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            return false;
        }
    }

    @Override
    public boolean updateUserById(User user) {
        String sql = "UPDATE " + TABLE_NAME
                + " SET email = ?, password = ?, isAdmin = ?, isSubscribed = ?, fullName = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isAdmin());
            statement.setBoolean(4, user.isSubscribed());
            statement.setString(5, user.getFullName());
            statement.setInt(6, user.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            return false;
        }
    }

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO " + TABLE_NAME
                + " (email, password, isAdmin, isSubscribed, fullName) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, false);
            statement.setBoolean(4, false);
            statement.setString(5, user.getFullName());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            return false;
        }
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email LIKE ? OR fullName LIKE ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    user.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return users;
    }

    // the interface methods override

    @Override
    public List<User> findAll() {
        System.out.println("\nthe function findAll in Overrided in the UserRepository from Repository<User, Integer>");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    user.setCreatedAt(resultSet.getTimestamp("createdAt"));

                    // Populate other user fields if needed
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
        }
        System.out.println("list of users");
        System.out.println(users);
        return users;
    }

    @Override
    public boolean save(User entity) {
        System.out.println("\nthe function save(User entity) in Overrided in the UserRepository from Repository<User, Integer>");
        String sql = "INSERT INTO " + TABLE_NAME
                + " (email, password, isAdmin, isSubscribed, fullName) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());
            statement.setBoolean(3, entity.isAdmin());
            statement.setBoolean(4, entity.isSubscribed());
            statement.setString(5, entity.getFullName());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        System.out.println("\nthe function deleteById(Integer id) in Overrided in the UserRepository from Repository<User, Integer>");
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            return false;
        }
    }

    @Override
    public List<User> searchByKeyword(String keyword) {
        System.out.println("\nthe function searchByKeyword(String keyword) in Overrided in the UserRepository from Repository<User, String>");
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE email LIKE ? OR fullName LIKE ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    user.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return users;
    }

}
