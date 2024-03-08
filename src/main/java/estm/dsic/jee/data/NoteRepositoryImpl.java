package estm.dsic.jee.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import javax.sql.DataSource;

import estm.dsic.jee.data.gen.Repository;
import estm.dsic.jee.models.Note;

@Named
@SessionScoped
public class NoteRepositoryImpl implements NoteRepository, Serializable, Repository<Note, Integer> {

    @Resource(lookup = "jdbc/g_note")
    private DataSource dataSource;

    private static final String TABLE_NAME = "note";

    @Override
    public List<Note> getNotesByOwnerId(int ownerId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ownerId = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setIdNote(resultSet.getInt("idNote"));
                    note.setOwnerId(resultSet.getInt("ownerId"));
                    note.setSubject(resultSet.getString("Subject"));
                    note.setBody(resultSet.getString("Body"));
                    note.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    note.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public boolean saveNote(Note note) {
        String sql = "INSERT INTO " + TABLE_NAME + " (ownerId, Subject, Body) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, note.getOwnerId());
            statement.setString(2, note.getSubject());
            statement.setString(3, note.getBody());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Note note) {
        String sql = "UPDATE " + TABLE_NAME + " SET Subject = ?, Body = ? WHERE idNote = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, note.getSubject());
            statement.setString(2, note.getBody());
            statement.setInt(3, note.getIdNote());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idNote = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Note> searchNotesByKeyword(int userId, String keyword) {
        System.out.println("in the repository" + userId + "  " + keyword);
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ownerId = ? AND (Subject LIKE ? OR Body LIKE ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.setString(2, "%" + keyword + "%");
            statement.setString(3, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setIdNote(resultSet.getInt("idNote"));
                    note.setOwnerId(resultSet.getInt("ownerId"));
                    note.setSubject(resultSet.getString("Subject"));
                    note.setBody(resultSet.getString("Body"));
                    note.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    note.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setIdNote(resultSet.getInt("idNote"));
                    note.setOwnerId(resultSet.getInt("ownerId"));
                    note.setSubject(resultSet.getString("Subject"));
                    note.setBody(resultSet.getString("Body"));
                    note.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    note.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return notes;
    }

    @Override
    public boolean save(Note entity) {
        System.out.println("\nthe function save(Note entity) in Overrided in the NoteRepository from Repository<Note, Integer>");
        String sql = "INSERT INTO " + TABLE_NAME + " (ownerId, Subject, Body) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getOwnerId());
            statement.setString(2, entity.getSubject());
            statement.setString(3, entity.getBody());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE idNote = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
            return false;
        }
    }

    @Override
    public List<Note> searchByKeyword(String keyword) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE Subject LIKE ? OR Body LIKE ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setIdNote(resultSet.getInt("idNote"));
                    note.setOwnerId(resultSet.getInt("ownerId"));
                    note.setSubject(resultSet.getString("Subject"));
                    note.setBody(resultSet.getString("Body"));
                    note.setCreatedAt(resultSet.getTimestamp("createdAt"));
                    note.setUpdatedAt(resultSet.getTimestamp("updatedAt"));
                    notes.add(note);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return notes;
    }

}
