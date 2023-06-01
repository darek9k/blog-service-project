package darek9k.post;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long>{
    List<Post> findByStatus(PostStatus poststatus);

    long countByStatus(PostStatus poststatus);

    boolean existsByStatus(PostStatus poststatus);

}
