package darek9k.comment;

import darek9k.post.Post;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;

    public Comment() {
    }

    public Comment(String text, String author, Post post) {
        this.text = text;
        this.author = author;
        this.post = post;
    }

    public Comment(Comment comment){
        this.id = comment.id;
        this.createdDateTime = comment.createdDateTime;
        this.lastModifiedDate = comment.lastModifiedDate;
        this.text = comment.text;
        this.author = comment.author;
        this.post = comment.post;

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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDate=" + lastModifiedDate +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
