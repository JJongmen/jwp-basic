package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.QuestionDao;

public class HomeController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        ModelAndView mv = new ModelAndView("home.jsp");
        mv.setAttribute("questions", questionDao.findAll());
        return mv;
    }
}
