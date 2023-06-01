package darek9k.post;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(CreatePostRequest postRequest){
        Post post = new Post(
                postRequest.getText(),
                postRequest.getScope(),
                postRequest.getAuthor(),
                postRequest.getPublicationDate()
        );
        postRepository.save(post);
    }

    public ReadPostResponse findById(Long id) {
        return postRepository.findById(id)//gives optional Post
                .map(ReadPostResponse::from)//mapping Optional<Post> to Optional<ReadPostResponse>
                .orElseThrow(EntityNotFoundException::new);//either give ReadPostResponse or throw an exception.
    }

    public void update(Long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Post newPost = new Post(post);

        newPost.setText(updatePostRequest.getText());
        newPost.setScope(updatePostRequest.getScope());
        newPost.setVersion(updatePostRequest.getVersion());

        postRepository.save(newPost);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void archive(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Post newPost = new Post(post);
        newPost.setStatus(PostStatus.DELETED);
        postRepository.save(newPost);
    }

    public void find() {
        log(postRepository.findByStatusOrderByCreatedDateTimeDesc(PostStatus.ACTIVE), "findByStatusOrderByCreatedDateTimeDesc");

        log(postRepository.findByStatus(PostStatus.ACTIVE,
                        Sort.by("CreatedDateTime")
                ), "findByStatus"
        );

        log(postRepository.findByStatus(PostStatus.ACTIVE,
                        Sort.by("CreatedDateTime", "author")
                ), "findByStatus"
        );

        log(postRepository.findByStatus(PostStatus.ACTIVE,
                        Sort.by(Sort.Order.asc("CreatedDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );
        /*System.out.println(postRepository.countByStatus(PostStatus.DELETED));
        System.out.println(postRepository.existsByStatus(PostStatus.ACTIVE));

        log(postRepository.findByStatusAndAuthor(PostStatus.ACTIVE,"Darek Kowalski2"), "findByStatusAndAuthor");

        log(postRepository.findByStatusInAndAuthorLike(Set.of(PostStatus.ACTIVE),"Darek Kowalski"), "findByStatusInAndAuthorLike");
        log(postRepository.findByStatusInAndAuthorContaining(Set.of(PostStatus.ACTIVE),"Darek Kowalski"), "findByStatusInAndAuthorContaining");
        log(postRepository.findByStatusInAndAuthorStartingWith(Set.of(PostStatus.ACTIVE),"Darek Kowalski"), "findByStatusInAndAuthorStartingWith");

        log(postRepository.findByStatusInAndAuthorStartingWith(Set.of(),"Darek Kowalski"), "findByStatusInAndAuthorStartingWith");

        log(postRepository.findByStatusInAndCreatedDateTimeBetween(Set.of(PostStatus.DELETED),
                LocalDate.of(2023,5,23).atStartOfDay(),
                LocalDate.of(2023,5,27).atStartOfDay()
                ), "findByStatusInAndCreatedDateTimeBetween"
        );*/

    }
    private void log(List<Post> posts, String methodName){
        System.out.println("--------------------"+methodName+"----------------------");
        posts.forEach(System.out::println);
    }
}
