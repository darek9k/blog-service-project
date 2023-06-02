package darek9k.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PostRepository extends CrudRepository<Post, Long>{
    @Query("select p from Post p")
    List<Post> find();

    List<Post> findByStatus(PostStatus poststatus, Sort sort);
    List<Post> findByStatusOrderByCreatedDateTimeDesc(PostStatus poststatus);

    List<Post> findByStatusAndAuthor(PostStatus poststatus, String author);

    List<Post> findByStatusInAndAuthorLike(Set<PostStatus> postStatuses, String Author);

    List<Post> findByStatusInAndAuthorContaining(Set<PostStatus> postStatuses, String Author);

    List<Post> findByStatusInAndAuthorStartingWith(Set<PostStatus> postStatuses, String Author);

    List<Post> findByStatusInAndCreatedDateTimeBetween(Set<PostStatus> postStatuses, LocalDateTime startDate, LocalDateTime endDate);

    long countByStatus(PostStatus poststatus);

    boolean existsByStatus(PostStatus poststatus);

}
