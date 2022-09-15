package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        ModelAndView mv = new ModelAndView("/user/list.jsp");
        mv.setAttribute("users", userDao.findAll());
        return mv;
    }
}
