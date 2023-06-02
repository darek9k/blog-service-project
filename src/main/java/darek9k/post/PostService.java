package darek9k.post;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
                        Sort.by("createdDateTime")
                ), "findByStatus"
        );

        log(postRepository.findByStatus(PostStatus.ACTIVE,
                        Sort.by("createdDateTime", "author")
                ), "findByStatus"
        );

        log(postRepository.findByStatus(PostStatus.ACTIVE,
                        Sort.by(Sort.Order.asc("createdDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );
        System.out.println(postRepository.countByStatus(PostStatus.DELETED));
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
        );

        //log(postRepository::find,"find");

        log(() -> postRepository.findByStatusOrderByCreatedDateTimeDesc(PostStatus.DELETED), "findByStatusOrderByCreatedDateTimeDesc");

        log(() -> postRepository.findOrderByCreatedDateTimeDesc(PostStatus.DELETED), "findOrderByCreatedDateTimeDesc");

        log(() -> postRepository.findByStatus(PostStatus.DELETED,
                        Sort.by(Sort.Order.desc("createdDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );
        log(() -> postRepository.findAndSort(PostStatus.DELETED,
                        Sort.by(Sort.Order.desc("createdDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );

        log(() -> postRepository.findByStatusInAndAuthorLike(Set.of(PostStatus.ACTIVE), "Darek Kowalski"), "findByStatusInAndAuthorLike");
        log(() -> postRepository.find(Set.of(PostStatus.ACTIVE, PostStatus.DELETED), "Kowalski"), "findSetLikeAuthor");

        System.out.println("=============================================================================================================================");
        System.out.println("find");
        System.out.println(postRepository.find(1L));

       System.out.println("findOptional");
        System.out.println(postRepository.findOptional(4333L));

        System.out.println("count");
        System.out.println(postRepository.count());

        System.out.println("findIds");
        System.out.println(postRepository.findIds());

        System.out.println("findAuthors");
        System.out.println(postRepository.findAuthors());

        /*System.out.println("find po statusie");
        System.out.println(postRepository.find(PostStatus.ACTIVE));*/

        log(() -> postRepository.findByStatus(PostStatus.ACTIVE,
                        PageRequest.of(0, 2, Sort.by(Sort.Order.desc("id")))
                ),
                "findByStatus"
        );
        log(() -> postRepository.findAndSort(PostStatus.ACTIVE,
                PageRequest.of(0, 2, Sort.by(Sort.Order.desc("id")))
        ),
                "findAndSort"
        );


    }
    private void log(List<Post> posts, String methodName){
        System.out.println("--------------------"+methodName+"----------------------");
        posts.forEach(System.out::println);
    }

    private void log(Supplier<List<Post>> listSupplier, String methodName) {
        System.out.println("--------------------" + methodName + "----------------------");
        listSupplier.get().forEach(System.out::println);
    }
}
