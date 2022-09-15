package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static org.slf4j.LoggerFactory.getLogger;

public class DeleteAnswerController implements Controller {
    private final Logger log = getLogger(DeleteAnswerController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long answerId = Long.parseLong(req.getParameter("answerId"));
        log.debug("answerId : {}", answerId);
        AnswerDao answerDao = new AnswerDao();
        answerDao.remove(answerId);

        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(mapper.writeValueAsString(Result.ok()));
        return null;
    }
}
