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

public class UpdateFormUserController implements Controller {
    private final Logger log = getLogger(UpdateFormUserController.class);


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        req.setAttribute("user", user);

        return "/user/updateForm.jsp";
    }
}
