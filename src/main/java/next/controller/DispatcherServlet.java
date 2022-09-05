package next.controller;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private final Logger log = getLogger(DispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("URL : {}", req.getRequestURI());
        RequestMapping.getController(req.getRequestURI()).service(req, resp);
    }
}
