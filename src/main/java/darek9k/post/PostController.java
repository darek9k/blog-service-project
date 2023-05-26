package darek9k.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public void read() {
        Optional<Post> optionalPost = postRepository.findById(5L);
        Post post = optionalPost.get();
        System.out.println(post);
    }

    @PostMapping
    public void create() {
        for (int i = 0; i < 20; i++) {

            postRepository.save(new Post(
                    null,
                    "Example text" +i,
                    LocalDateTime.now(),
                    i%2==0 ? PostScope.PRIVATE : PostScope.PUBLIC,
                    "Darek"+i,
                    null,
                    (i<10)?PostStatus.ACTIVE:PostStatus.DELETED
            ));
        }
    }

}