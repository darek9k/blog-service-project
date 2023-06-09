package darek9k.comment;

import darek9k.post.Post;
import darek9k.post.PostService;
import darek9k.util.SpecificationUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostService postService;

   @Transactional
    public void create(CreateCommentRequest createCommentRequest){
        Post post = postService.findPostById(createCommentRequest.getPostId());

       Comment comment = Comment.builder()
                .text(createCommentRequest.getText())
                .author(createCommentRequest.getAuthor())
                .post(post)
                .build();


        /*Comment comment = new Comment(
                createCommentRequest.getText(),
                createCommentRequest.getAuthor(),
                post
        );*/
        commentRepository.save(comment);
    }

    //@Transactional(readOnly = true)
    public ReadCommentResponse findById(Long id) {
        Optional<Comment> maybeComment = commentRepository.findByIdFetchPost(id);
        log.debug("maybeComment: {}", maybeComment);
        Optional<ReadCommentResponse> readCommentResponse = maybeComment.map(ReadCommentResponse::from);
        log.debug("readCommentResponse: {}", readCommentResponse);
        ReadCommentResponse comment = readCommentResponse.orElseThrow(EntityNotFoundException::new);
        log.debug("comment: {}", comment);
        return comment;
    }

    @Transactional
    public void update(Long id, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Comment newComment = comment.toBuilder()
                .author(updateCommentRequest.getAuthor())
                .text(updateCommentRequest.getText())
                .build();

        commentRepository.save(newComment);
    }

    public Page<ReadCommentResponse> find(Long postId, Pageable pageable) {
        return commentRepository.findAll(prepareSpecification(postId), pageable)
                .map(ReadCommentResponse::from);
    }

    private Specification<Comment> prepareSpecification(Long postId) {
        return (root, query, criteriaBuilder) -> {
            if (!SpecificationUtil.isCountQuery(query)) {
                root.fetch("post");
            }
            return criteriaBuilder.equal(root.get("post").get("id"), postId);
        };
    }
}

