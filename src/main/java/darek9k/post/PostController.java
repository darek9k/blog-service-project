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
        return ResponseEntity.ok(postService.findById(id));
    }

    @PostMapping
    public void create(@Valid @RequestBody CreatePostRequest postRequest) {
        postService.create(postRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody UpdatePostRequest updatePostRequest) {
        postService.update(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/archive")
    public ResponseEntity<Void> update(@PathVariable("id") Long id) {
        postService.archive(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Void> find(){
        postService.find();
        return ResponseEntity.noContent().build();
    }
}
