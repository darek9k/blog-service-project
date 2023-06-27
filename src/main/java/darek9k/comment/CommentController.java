package darek9k.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public void create(@Valid @RequestBody CreateCommentRequest createCommentRequest) {
        commentService.create(createCommentRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadCommentResponse> read(@PathVariable("id") Long id) {
        log.info("metoda read, paramtry: {}", id);
        long start = System.currentTimeMillis();
        ReadCommentResponse comment = commentService.findById(id);
        ResponseEntity<ReadCommentResponse> ok = ResponseEntity.ok(comment);
        long end = System.currentTimeMillis();
        log.info("koniec metody read, trwala: {} ms", (end-start));
        return ok;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        commentService.update(id, updateCommentRequest);
        return ResponseEntity.ok().build();
    }
}
