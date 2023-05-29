package darek9k.post;

import java.time.LocalDateTime;

public class ReadPostResponse {

    private final Long id;

    private final String text;

    private final LocalDateTime createdDate;

    private final PostScope scope;

    private final String author;

    private final LocalDateTime publicationDate;
    private final PostStatus status;

    public ReadPostResponse(Long id, String text, LocalDateTime createdDate, PostScope scope, String author, LocalDateTime publicationDate, PostStatus status) {
        this.id = id;
        this.text = text;
        this.createdDate = createdDate;
        this.scope = scope;
        this.author = author;
        this.publicationDate = publicationDate;
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

    public static ReadPostResponse from(Post post){
        return new ReadPostResponse(
                post.getId(),
                post.getText(),
                post.getCreatedDate(),
                post.getScope(),
                post.getAuthor(),
                post.getPublicationDate(),
                post.getStatus()
        );
    }
}