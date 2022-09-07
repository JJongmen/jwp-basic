package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import org.slf4j.Logger;

import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class HomeController implements Controller {
    private final Logger log = getLogger(HomeController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserDao userDao = new UserDao();
        try {
            req.setAttribute("users", userDao.findAll());
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "home.jsp";
    }
}
