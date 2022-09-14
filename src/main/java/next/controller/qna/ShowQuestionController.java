package next.controller.qna;


import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findByQuestionId(questionId);
        AnswerDao answerDao = new AnswerDao();
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        req.setAttribute("question", question);
        req.setAttribute("answers", answers);
        return "/qna/show.jsp";
    }
}
