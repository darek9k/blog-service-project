package darek9k.post;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    public final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public void read() {
    }

    @PostMapping
    public void create(@RequestBody CreatePostRequest postRequest) {
        postService.create(postRequest);
    }

}
