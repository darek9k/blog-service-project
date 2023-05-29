package darek9k.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    @Size(max = 5000)
    private String text;
    @NotNull
    private LocalDateTime createdDate;
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

    public Post() {
    }

    public Post(String text, PostScope scope, String author, LocalDateTime publicationDate) {
        this.text = text;
        this.createdDate = LocalDateTime.now();
        this.scope = scope;
        this.author = author;
        this.publicationDate = publicationDate;
        this.status = PostStatus.ACTIVE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setScope(PostScope scope) {
        this.scope = scope;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public PostScope getScope() {
        return scope;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public PostStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdDate=" + createdDate +
                ", scope=" + scope +
                ", author='" + author + '\'' +
                ", publicationDate=" + publicationDate +
                ", status=" + status +
                '}';
    }
}