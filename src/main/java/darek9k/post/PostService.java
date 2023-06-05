package darek9k.post;

import darek9k.util.LogUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    public Page<FindPostResponse> find(Pageable pageable) {
        Specification<Post> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), PostStatus.ACTIVE);

        return postRepository.findAll(specification, pageable)
                .map(FindPostResponse::from);
    }

    public Page<FindPostResponse> find(String textContaining,
                                       int page,
                                       int size) {
        return postRepository.findActiveAndPublished(textContaining,
                        LocalDateTime.now(),
                        PageRequest.of(page,
                                size,
                                Sort.by(Sort.Order.desc("createdDateTime"))))
                .map(FindPostResponse::from);
    }

    public void find2() {
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

        LogUtil.log(() -> postRepository.findByStatusOrderByCreatedDateTimeDesc(PostStatus.DELETED), "findByStatusOrderByCreatedDateTimeDesc");

        LogUtil.log(() -> postRepository.findOrderByCreatedDateTimeDesc(PostStatus.DELETED), "findOrderByCreatedDateTimeDesc");

        LogUtil.log(() -> postRepository.findByStatus(PostStatus.DELETED,
                        Sort.by(Sort.Order.desc("createdDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );
        LogUtil.log(() -> postRepository.findAndSort(PostStatus.DELETED,
                        Sort.by(Sort.Order.desc("createdDateTime"), Sort.Order.desc("author"))
                ), "findByStatus"
        );

        LogUtil.log(() -> postRepository.findByStatusInAndAuthorLike(Set.of(PostStatus.ACTIVE), "Darek Kowalski"), "findByStatusInAndAuthorLike");
        LogUtil.log(() -> postRepository.find(Set.of(PostStatus.ACTIVE, PostStatus.DELETED), "Kowalski"), "findSetLikeAuthor");

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

        LogUtil.log(() -> postRepository.findByStatus(PostStatus.ACTIVE, PageRequest.of(0, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus");
        LogUtil.log(() -> postRepository.findByStatus(PostStatus.ACTIVE, PageRequest.of(1, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus");
        LogUtil.log(() -> postRepository.findByStatus(PostStatus.ACTIVE, PageRequest.of(2, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus");
        LogUtil.log(() -> postRepository.findByStatus(PostStatus.ACTIVE, PageRequest.of(3, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus");

        LogUtil.logPage(() -> postRepository.findAllByStatus(PostStatus.ACTIVE, PageRequest.of(0, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus Page 0");
        LogUtil.logPage(() -> postRepository.findAllByStatus(PostStatus.ACTIVE, PageRequest.of(1, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus Page 1");
        LogUtil.logPage(() -> postRepository.findAllByStatus(PostStatus.ACTIVE, PageRequest.of(2, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus Page 2");
        LogUtil.logPage(() -> postRepository.findAllByStatus(PostStatus.ACTIVE, PageRequest.of(3, 2, Sort.by(Sort.Order.desc("id")))), "findByStatus Page 3");
    }
    private void log(List<Post> posts, String methodName){
        System.out.println("--------------------"+methodName+"----------------------");
        posts.forEach(System.out::println);
    }

}
