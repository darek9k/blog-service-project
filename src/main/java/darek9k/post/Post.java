package darek9k.post;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private PostScope scope;

    private String author;

    private LocalDateTime publicationDate;
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    public Post(){}

    public Post(Long id, String text, LocalDateTime createdDate, PostScope scope, String author, LocalDateTime publicationDate, PostStatus status) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.scope = scope;
        this.author = author;
        this.publicationDate = publicationDate;
        this.status = status;
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
