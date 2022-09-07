package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;

import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginController implements Controller {
    private final Logger log = getLogger(LoginController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        try {
            User user = userDao.findByUserId(userId);
            if (user == null) {
                req.setAttribute("loginFailed", true);
                return "/user/login.jsp";
            }
            if (user.matchPassword(password)) {
                HttpSession session = req.getSession();
                session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
                return "redirect:/";
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        req.setAttribute("loginFailed", true);
        return "/user/login.jsp";
    }
}
