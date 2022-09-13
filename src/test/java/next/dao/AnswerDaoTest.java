package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnswerDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void findByAnswerId() throws Exception {
        /**
         * INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
         * ('eungju',
         * 'http://underscorejs.org/docs/underscore.html Underscore.js 강추합니다!
         * 쓸일도 많고, 코드도 길지 않고, 자바스크립트의 언어나 기본 API를 보완하는 기능들이라 자바스크립트 이해에 도움이 됩니다. 무엇보다 라이브러리 자체가 아주 유용합니다.',
         * CURRENT_TIMESTAMP(), 7);
         */
        AnswerDao answerDao = new AnswerDao();
        Answer findAnswer = answerDao.findByAnswerId(1);
        assertEquals("eungju", findAnswer.getWriter());
        assertEquals("http://underscorejs.org/docs/underscore.html Underscore.js 강추합니다!\n" +
                "쓸일도 많고, 코드도 길지 않고, 자바스크립트의 언어나 기본 API를 보완하는 기능들이라 자바스크립트 이해에 도움이 됩니다. 무엇보다 라이브러리 자체가 아주 유용합니다.",
                findAnswer.getContents());
        assertEquals(7, findAnswer.getQuestionId());
    }

    @Test
    public void findAllByQuestionId() throws Exception {
        /**
         * INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
         * ('eungju',
         * 'http://underscorejs.org/docs/underscore.html Underscore.js 강추합니다!
         * 쓸일도 많고, 코드도 길지 않고, 자바스크립트의 언어나 기본 API를 보완하는 기능들이라 자바스크립트 이해에 도움이 됩니다. 무엇보다 라이브러리 자체가 아주 유용합니다.',
         * CURRENT_TIMESTAMP(), 7);
         *
         * INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES
         * ('Hanghee Yi',
         * '언더스코어 강력 추천드려요.
         * 다만 최신 버전을 공부하는 것보다는 0.10.0 버전부터 보는게 더 좋더군요.
         * 코드의 변천사도 알 수 있고, 최적화되지 않은 코드들이 기능은 그대로 두고 최적화되어 가는 걸 보면 재미가 있습니다 :)',
         * CURRENT_TIMESTAMP(), 7);
         */
        AnswerDao answerDao = new AnswerDao();
        List<Answer> answers = answerDao.findAllByQuestionId(7);
        assertEquals(2, answers.size());
    }

    @Test
    public void insert() throws Exception {
        Answer answer = new Answer(100, "JYP", "좋은 내용 감사합니다!", new Timestamp(System.currentTimeMillis()), 7);
        AnswerDao answerDao = new AnswerDao();

        answerDao.insert(answer);
        List<Answer> answers = answerDao.findAllByQuestionId(7);
        assertEquals(3, answers.size());
    }
}
