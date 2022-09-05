package next.controller;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private final Logger log = getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        log.debug("DispatcherServlet init");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("URL : {}", req.getRequestURI());
        try {
            String viewPath = RequestMapping.getController(req.getRequestURI()).execute(req, resp);
            if (viewPath.startsWith("redirect:")) {
                String redirectPath = viewPath.substring(viewPath.indexOf(':') + 1);
                resp.sendRedirect(redirectPath);
                log.debug("Redirect : {}", redirectPath);
                return;
            }
            RequestDispatcher rd = req.getRequestDispatcher(viewPath);
            rd.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
