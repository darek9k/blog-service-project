package darek9k.comment;

import darek9k.post.Post;
import darek9k.post.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }

    public void create(CreateCommentRequest createCommentRequest){
        Post post = postService.findPostById(createCommentRequest.getPostId());

        Comment comment = new Comment(
                createCommentRequest.getText(),
                createCommentRequest.getAuthor(),
                post
        );
        commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        Optional<Comment> maybeComment = commentRepository.findById(id);

        Comment comment = maybeComment.orElseThrow(EntityNotFoundException::new);
        return comment;
    }
}
