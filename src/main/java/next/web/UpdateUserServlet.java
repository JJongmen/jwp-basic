package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    private final Logger log = getLogger(UpdateUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        log.debug("userId : {}", userId);
        User user = DataBase.findUserById(userId);
        log.debug("user : {}", user);
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/update.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));
        user.update(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        resp.sendRedirect("/user/list");
    }
}
