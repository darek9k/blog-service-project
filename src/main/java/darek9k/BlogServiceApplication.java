package darek9k;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlogServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BlogServiceApplication.class, args);
	}

}
