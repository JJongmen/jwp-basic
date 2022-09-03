package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    private final Logger log = getLogger(ListUserServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        log.debug("user : {}", user);
        if (user == null) {
            resp.sendRedirect("/user/login.html");
        } else {
            req.setAttribute("users", DataBase.findAll());
            RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
            rd.forward(req, resp);
        }
    }
}
