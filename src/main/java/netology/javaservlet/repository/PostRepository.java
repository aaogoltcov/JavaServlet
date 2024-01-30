package netology.javaservlet.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import netology.javaservlet.model.Post;

public class PostRepository {
    public static ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong lastPostId = new AtomicLong(0);

    public List<Post> all() {
        if (posts.isEmpty()) {
            return new ArrayList<>();
        }

        return posts.values().stream().toList();
    }

    public Post getById(long id) {
        return posts.get(id);
    }

    public Post save(Post post) {
        final long id = post.getId();
        final Post existedPost = posts.get(id);

        if (existedPost != null) {
            existedPost.setContent(post.getContent());

            return existedPost;
        }

        final long nextId = getNextId();
        Post newPost = new Post(nextId, post.getContent());

        posts.put(nextId, newPost);

        return newPost;
    }

    public void removeById(long id) {
        posts.remove(id);
    }

    private long getNextId() {
        return lastPostId.addAndGet(1);
    }
}
