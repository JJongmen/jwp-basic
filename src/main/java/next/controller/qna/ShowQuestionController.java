package next.controller.qna;


import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String questionId = req.getParameter("questionId");
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(Long.parseLong(questionId));
        req.setAttribute("question", question);
        return "/qna/show.jsp";
    }
}
