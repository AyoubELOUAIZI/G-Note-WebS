package estm.dsic.jee.models;

import java.sql.Timestamp;

public class Note {
    private int idNote;
    private int ownerId;
    private String subject;
    private String body;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Note() {}

    @Override
    public String toString() {
        return "Note [idNote=" + idNote + ", ownerId=" + ownerId + ", subject=" + subject + ", body=" + body
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    public Note(int ownerId, String subject, String body) {
        this.ownerId = ownerId;
        this.subject = subject;
        this.body = body;
    }

    // Getters and setters
    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
