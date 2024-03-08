package estm.dsic.jee.data.gen;

import jakarta.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepositoryImpl<T, I> implements GenericRepository<T, I> {

    @Resource(lookup = "jdbc/g_note")
    private DataSource dataSource;

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName(); // Assuming getTableName() returns the table name

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet); // Assuming you have a method to map ResultSet to entity
                entities.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return entities;
    }

    @Override  
    public boolean save(T entity) {
        String query = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?)"; // Assuming the values to insert
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set values for insertion (assuming the columns are id, name, age, etc.)
            // statement.setXXX(index, value);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        String query = "UPDATE " + getTableName() + " SET ... WHERE ..."; // Assuming the update query
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set values for update (assuming the columns to be updated)
            // statement.setXXX(index, value);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    @Override
    public boolean deleteById(I id) {//I am starting with this most easy one
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?"; // Assuming the delete query
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, id);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return false;
        }
    }

    @Override
    public List<T> searchByKeyword(String keyword) {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName() + " WHERE ..."; // Assuming the search query
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    T entity = mapResultSetToEntity(resultSet); // Assuming you have a method to map ResultSet to entity
                    entities.add(entity);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return entities;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();
}
