package darek9k.post;

import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Post> maybePost = postRepository.findById(id);

        Post post = maybePost.orElseThrow();

        return ReadPostResponse.from(post);
    }
}
