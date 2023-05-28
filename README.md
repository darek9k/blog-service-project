Blog service-project
project from Java course

First project using a database. Blogging service.
I am starting with simple functions to explore the benefits of Spring Boot and Hibernate.

How I'm starting:
Adding dependencies to pom.xml

h2 and spring-boot-starter-data-jpa

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

I am building the NameRepository interface based on inheritance from CrudRepository.
```
public interface PostRepository extends CrudRepository<Post, Long>{}
```

I am injecting it into the service layer.
```
private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
```

Given that there are certain fields in the post that I don't want the user to provide, such as id, createDate, and status, I will restrict the fields in the entity's constructor. I will remove the id field and assign fixed values to the createDate and status fields.

Post entity.
```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String text;

private LocalDateTime createdDate;

@Enumerated(EnumType.STRING)
private PostScope scope;

private String author;

private LocalDateTime publicationDate;

@Enumerated(EnumType.STRING)
private PostStatus status;
```
There is an additional immutable class called CreatePostRequest, which serves as a data carrier from the controller class to the service class. In the service class, it is reconstructed into the Post entity.

```
public void create(CreatePostRequest postRequest){
        Post post = new Post(
                postRequest.getText(),
                postRequest.getScope(),
                postRequest.getAuthor(),
                postRequest.getPublicationDate()
        );
        postRepository.save(post);
}
```

As a separate homework assignment, I am simultaneously performing similar operations with the Invoice entity. The goal is to generate an invoice report.

