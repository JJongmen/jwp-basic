package next.controller.qna;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

public class CreateQuestionController implements Controller {
    private final Logger log = getLogger(CreateQuestionController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(new Question(req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents")));
        return "redirect:/";
    }
}
