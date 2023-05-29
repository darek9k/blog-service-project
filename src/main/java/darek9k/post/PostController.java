package darek9k.post;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    public final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadPostResponse> read(@PathVariable("id") Long id) {
        ReadPostResponse readPostResponse = postService.findById(id);
        return ResponseEntity.ok(readPostResponse);
    }

    @PostMapping
    public void create(@Valid @RequestBody CreatePostRequest postRequest) {
        postService.create(postRequest);
    }
}
