package netology.javaservlet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import netology.javaservlet.controller.PostController;
import netology.javaservlet.repository.PostRepository;
import netology.javaservlet.service.PostService;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController() {
        return new PostController(postService());
    }

    @Bean
    public PostService postService() {
        return new PostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}
