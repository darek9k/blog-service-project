package darek9k.post;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //Stworzyć usługę typu GET, która zwraca liste postów, ktore są ACTIVE i opublikowane, posortowane od najnowszych
    // dostępna pod adresem /api/posts
    //parametry
    //q - filtruje po polu text, usluga zwraca posty, które zawieają wartość w polu text, wartość podaną w polu q
    //page - numer strony, wymagany
    //size - rozmiar strony, wymagany
    //
    //response
    //text skrócony do 50 znaków, datę utworzenia i autor

    // Create a GET service that returns a list of posts that are ACTIVE and published, sorted from newest to oldest.
    // available at /api/posts
    // parameters
    // q - filters by the text field, the service returns posts that contain the value specified in the q field
    // page - page number, required
    // size - page size, required
    //
    // response
    // text shortened to 50 characters, creation date, and author.

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
    public ResponseEntity<Page<FindPostResponse>> find(@RequestParam(value = "q", defaultValue = "") String textContaining,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        return ResponseEntity.ok(postService.find(textContaining, page, size));
    }

    @PostMapping("/find")
    public ResponseEntity<Page<FindPostResponse>> find(Pageable pageable) {
        return ResponseEntity.ok(postService.find(pageable));
    }
}
