package darek9k.comment;

import darek9k.post.ReadPostResponse;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ReadCommentResponse {

    Long id;

    String text;

    LocalDateTime createdDateTime;

    String author;

    ReadPostResponse post;


    public static ReadCommentResponse from(Comment comment) {
        return new ReadCommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getCreatedDateTime(),
                comment.getAuthor(),
                ReadPostResponse.from(comment.getPost())
        );
    }
}
