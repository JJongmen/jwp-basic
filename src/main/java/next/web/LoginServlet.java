package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (user == null) {
            resp.sendRedirect("/user/login_failed.html");
        } else {
            if (user.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("/index.jsp");
            }
        }
    }
}
