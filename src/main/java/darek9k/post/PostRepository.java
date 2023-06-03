package darek9k.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends CrudRepository<Post, Long>{

    @Query("select p from Post p where p.id=:id")
    Post find(Long id);

    @Query("select p from Post p")
    List<Post> find();

    @Query("select p from Post p where p.status='DELETED'")
    List<Post> findDeleted();

    @Query("select p.id from Post p where p.status='DELETED'")
    List<Long> findIds();

    @Query("select distinct p.author from Post p")
    List<String> findAuthors();

    @Query("select p from Post p where p.status=:status")
    Post find(PostStatus status);

    @Query("select p from Post p where p.id=:id")
    Optional<Post> findOptional(Long id);

    @Query("select count(p) from Post p")
    long count();


    List<Post> findByStatus(PostStatus postStatus, Sort sort);

    //paging
    List<Post> findByStatus(PostStatus postStatus, Pageable pageable);

    Page<Post> findAllByStatus(PostStatus postStatus, Pageable pageable);

    @Query("select p from Post p where p.status=:postStatus")
    List<Post> findAndSort(PostStatus postStatus, Sort sort);

    //paging
    @Query("select p from Post p where p.status=:postStatus")
    List<Post> findAndSort(PostStatus postStatus, Pageable pageable);

    @Query("select p from Post p where p.status=:postStatus")
    Page<Post> findWithPaging(PostStatus postStatus, Pageable pageable);

    List<Post> findByStatusOrderByCreatedDateTimeDesc(PostStatus postStatus);

    @Query("select p from Post p where p.status=:postStatus order by p.createdDateTime desc")
    List<Post> findOrderByCreatedDateTimeDesc(PostStatus postStatus);

    List<Post> findByStatusAndAuthor(PostStatus postStatus, String author);

    List<Post> findByStatusInAndAuthorLike(Set<PostStatus> postStatuses, String Author);

    @Query("select p from Post p where p.status in :postStatuses and p.author like %:author")
    List<Post> find(Set<PostStatus> postStatuses, @Param("author") String authorEndsWith);

    List<Post> findByStatusInAndAuthorContaining(Set<PostStatus> postStatuses, String Author);

    List<Post> findByStatusInAndAuthorStartingWith(Set<PostStatus> postStatuses, String Author);

    List<Post> findByStatusInAndCreatedDateTimeBetween(Set<PostStatus> postStatuses, LocalDateTime startDate, LocalDateTime endDate);

    long countByStatus(PostStatus postStatus);

    boolean existsByStatus(PostStatus postStatus);

}
