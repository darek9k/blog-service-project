package darek9k.post;

import darek9k.comment.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    @NotNull
    private Integer version;
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
    @NotNull
    @Enumerated(EnumType.STRING)
    private PostScope scope;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String author;
    @FutureOrPresent
    private LocalDateTime publicationDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    public Post() {
    }

    public Post(Post old) {
        this.id = old.id;
        this.version = old.version;
        this.createdDateTime = old.createdDateTime;
        this.lastModifiedDate = old.lastModifiedDate;
        this.text = old.text;
        this.scope = old.scope;
        this.author = old.author;
        this.publicationDate = old.publicationDate;
        this.status = old.status;
    }

    public Post(String text, PostScope scope, String author, LocalDateTime publicationDate) {
        this.text = text;
        this.scope = scope;
        this.author = author;
        this.publicationDate = publicationDate;
        this.status = PostStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public PostScope getScope() {
        return scope;
    }

    public void setScope(PostScope scope) {
        this.scope = scope;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", version=" + version +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDate=" + lastModifiedDate +
                ", text='" + text + '\'' +
                ", scope=" + scope +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", status=" + status +
                '}';
    }
}
