package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;


public class AddQuestionController extends AbstractController {
    private final Logger log = getLogger(AddQuestionController.class);
    private QuestionDao questionDao = new QuestionDao();
    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(session);
        String writer = user.getUserId();
        Question question = new Question(writer, request.getParameter("title"), request.getParameter("contents"));
        log.debug("question : {}", question);
        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
