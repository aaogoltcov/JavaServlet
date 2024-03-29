package netology.javaservlet.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import netology.javaservlet.controller.PostController;
import netology.javaservlet.repository.PostRepository;
import netology.javaservlet.service.PostService;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    private PostController controller;
    private static final String BASE_URL = "/api/posts";
    private static final String ENTITY_URL = BASE_URL + "/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (method.equals("GET") && path.equals(BASE_URL)) {
                controller.all(resp);

                return;
            }

            if (method.equals("GET") && path.matches(ENTITY_URL)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));

                controller.getById(id, resp);

                return;
            }

            if (method.equals("POST") && path.equals(BASE_URL)) {
                controller.save(req.getReader(), resp);

                return;
            }

            if (method.equals("DELETE") && path.matches(ENTITY_URL)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));

                controller.removeById(id, resp);

                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

