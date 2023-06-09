package darek9k.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @NotNull
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @NotNull
    private LocalDateTime lastModifiedDate;
    @NotBlank
    @NotNull
    @Size(max = 5000)
    private String text;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String author;
    @NotNull
    private Long postId;

    public Comment() {
    }

    public Comment(Comment old) {
        this.id = old.id;
        this.createdDateTime = old.createdDateTime;
        this.lastModifiedDate = old.lastModifiedDate;
        this.text = old.text;
        this.author = old.author;
        this.postId = old.postId;
    }

    public Comment(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDate=" + lastModifiedDate +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", postId=" + postId +
                '}';
    }
}
