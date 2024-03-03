package estm.dsic.jee.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.ResultSet;

import estm.dsic.jee.models.User;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserRepositoryImpl implements UserRepository, Serializable {

    @Resource(lookup = "jdbc/g_note")
    private DataSource dataSource;

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    // Populate other user fields if needed
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
    public User findByUsername(String username) {
        // Implementation to retrieve user by username from the database
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
                    user.setSubscribed(resultSet.getBoolean("isSubscribed"));
                    user.setFullName(resultSet.getString("fullName"));
                    // Populate other user fields if needed
                    System.out.println("\n\n\nuser in the repository \n"+user);
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
    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    // Populate other user fields if needed
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
    public boolean save(User user) {
        System.out.println("\n\n\nuser want to signup : " + user);
        String sql = "INSERT INTO user (email, password, isAdmin, isSubscribed) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.isAdmin());
            statement.setBoolean(4, user.isSubscribed());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Return true if at least one row was inserted
        } catch (SQLException e) {
            // Handle any SQL exceptions (e.g., logging, throwing custom exception)
            e.printStackTrace();
            return false; // Return false if an exception occurred
        }
    }

    @Override
    public boolean update(User user) {
        System.out.println("\n\n\nuser update" + user);
        // Implementation to update user information in the database
        String sql = "UPDATE user SET isSubscribed = ? WHERE id = ?";
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
}
