package next.controller.qna;

import core.mvc.Controller;
import next.controller.user.UserSessionUtils;
import next.dao.AnswerDao;
import next.model.Answer;
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
        String questionId = req.getParameter("questionId");
        log.debug("writer : {}", writer);
        log.debug("contents : {}", req.getParameter("contents"));
        log.debug("questionId : {}", questionId);
        answerDao.insert(new Answer(writer, req.getParameter("contents"), Long.parseLong(questionId)));
        return "redirect:/qna/show?questionId=" + questionId;
    }
}
