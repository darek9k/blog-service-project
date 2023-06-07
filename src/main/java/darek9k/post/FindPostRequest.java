package darek9k.post;

import java.time.LocalDateTime;
import java.util.Set;

public record FindPostRequest(Set<PostStatus> postStatuses,
                              String text,
                              LocalDateTime publicationDate,
                              LocalDateTime createdDateMin,
                              LocalDateTime createdDateMax) {

}
