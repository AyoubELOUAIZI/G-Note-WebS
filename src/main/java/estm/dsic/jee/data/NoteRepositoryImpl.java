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
import estm.dsic.jee.models.Note;

@Named
@SessionScoped
public class NoteRepositoryImpl implements NoteRepository, Serializable {

    @Resource(lookup = "jdbc/g_note")
    private DataSource dataSource;

    @Override
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM note";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public List<Note> getNotesByOwnerId(int ownerId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM note WHERE ownerId = ?";
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
    public boolean save(Note note) {
        String sql = "INSERT INTO note (ownerId, Subject, Body) VALUES (?, ?, ?)";
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
        String sql = "UPDATE note SET Subject = ?, Body = ? WHERE idNote = ?";
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
        String sql = "DELETE FROM note WHERE idNote = ?";
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
        System.out.println("in the repository"+userId+"  "+keyword);
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM note WHERE ownerId = ? AND (Subject LIKE ? OR Body LIKE ?)";
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

}
