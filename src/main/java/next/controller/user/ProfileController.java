package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.UserDao;
import next.model.User;

public class ProfileController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        ModelAndView mv = new ModelAndView("/user/profile.jsp");
        mv.setAttribute("user", user);
        return mv;
    }
}
