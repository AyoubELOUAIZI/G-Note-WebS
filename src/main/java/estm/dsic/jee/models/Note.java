package estm.dsic.jee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNote;

    // @ManyToOne // This annotation specifies a many-to-one relationship with the
    // User entity
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch strategy
    @JoinColumn(name = "OWNER_ID") // Map OWNER_ID to the corresponding column in the database
    private User owner;

    private String subject;
    private String body;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Note() {
    }

    // Getters and setters
    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    @Override
    public String toString() {
        return "Note [idNote=" + idNote + ", owner=" + owner + ", subject=" + subject + ", body=" + body
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    // This method will be called before persisting a new Note entity
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = this.createdAt; // Set updatedAt same as createdAt initially
    }

    // This method will be called before updating an existing Note entity
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
