package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;

import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class ProfileController implements Controller {
    private final Logger log = getLogger(ProfileController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        try {
            User user = userDao.findByUserId(userId);
            if (user == null) {
                throw new NullPointerException("사용자를 찾을 수 없습니다.");
            }
            req.setAttribute("user", user);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "/user/profile.jsp";
    }
}
