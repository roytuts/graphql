package spring.boot.graphql.rest.wrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "spring.boot.graphql.rest.wrapper")
public class SpringGraphqlServerApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringGraphqlServerApp.class, args);
	}

}
