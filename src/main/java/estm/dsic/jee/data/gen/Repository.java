package estm.dsic.jee.data.gen;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

public interface Repository<T, I> {
    List<T> findAll();

    boolean save(T entity);

    boolean update(T entity);

    boolean deleteById(I id);

    List<T> searchByKeyword(String keyword);

    default List<T> findAll(DataSource dataSource, String tableName, RowMapper<T> rowMapper) {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                T entity = rowMapper.mapRow(resultSet);
                entities.add(entity);
            }
            System.out.println("in the interface this is the list from database its generac: " + entities);

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }

        return entities;
    }

    interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }
}
