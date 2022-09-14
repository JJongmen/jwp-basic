package next.controller.qna;

import core.mvc.Controller;
import next.controller.user.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

public class CreateAnswerController implements Controller {
    private final Logger log = getLogger(CreateAnswerController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/users/loginForm";
        }
        AnswerDao answerDao = new AnswerDao();
        User user = UserSessionUtils.getUserFromSession(session);
        String writer = user.getName();
        long questionId = Long.parseLong(req.getParameter("questionId"));
        answerDao.insert(new Answer(writer, req.getParameter("contents"), questionId));
        return "redirect:/qna/show?questionId=" + questionId;
    }
}
