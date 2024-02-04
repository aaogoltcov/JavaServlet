package netology.javaservlet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import netology.javaservlet.model.Post;
import netology.javaservlet.repository.PostRepository;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> all() {
    return repository.all();
  }

  public Post getById(long id) {
    return repository.getById(id);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public void removeById(long id) {
    repository.removeById(id);
  }
}

