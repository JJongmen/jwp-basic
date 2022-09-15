package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        ModelAndView mv = new ModelAndView("/qna/show.jsp");
        mv.setAttribute("question", questionDao.findById(questionId));
        mv.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
        return mv;
    }
}
