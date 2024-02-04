package netology.javaservlet.controller;

import java.io.IOException;
import java.io.Reader;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;
import netology.javaservlet.exception.NotFoundException;
import netology.javaservlet.model.Post;
import netology.javaservlet.service.PostService;

@Controller
public class PostController {
    private static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        final var gson = new Gson();

        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        final var gson = new Gson();
        Post post = service.getById(id);

        response.setContentType(APPLICATION_JSON);

        if (post == null) {
            response.getWriter().print(new NotFoundException(String.format("Can't found post with id: %s", id)));

            return;
        }

        response.getWriter().print(gson.toJson(post));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);

        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);

        final var data = service.all();
        final var gson = new Gson();

        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data));
    }
}
