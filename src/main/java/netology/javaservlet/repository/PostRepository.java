package netology.javaservlet.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import netology.javaservlet.model.Post;

public class PostRepository {
    public static CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>();

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        return posts
                .stream()
                .filter(post -> post.getId() == id)
                .findFirst();
    }

    public Post save(Post post) {
        final long id = post.getId();
        Post existedPost = posts.stream().filter(item -> item.getId() == id).findFirst().orElse(null);

        if (existedPost != null) {
            existedPost.setContent(post.getContent());

            return existedPost;
        }

        Post newPost = new Post(getNextId(), post.getContent());

        posts.add(newPost);

        return newPost;
    }

    public void removeById(long id) {
        posts = posts
                .stream()
                .filter(post -> post.getId() != id)
                .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    private long getNextId() {
        final var lastId = posts.stream().map(Post::getId).max(Long::compareTo);

        return lastId.map(aLong -> aLong + 1L).orElse(1L);

    }
}
