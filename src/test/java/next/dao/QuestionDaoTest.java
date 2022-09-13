package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionDaoTest {
    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void findByQuestionId() throws Exception {
        /**
         * INSERT INTO QUESTIONS (questionId, writer, title, contents, createdDate, countOfAnswer) VALUES
         * (1, '자바지기',
         * '국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?',
         * 'Ruby on Rails(이하 RoR)는 2006년 즈음에 정말 뜨겁게 달아올랐다가 금방 가라 앉았다. Play 프레임워크는 정말 한 순간 잠시 눈에 뜨이다가 사라져 버렸다. RoR과 Play 기반으로 개발을 해보면 정말 생산성이 높으며, 웹 프로그래밍이 재미있기까지 하다. Spring MVC + JPA(Hibernate) 기반으로 진행하면 설정할 부분도 많고, 기본으로 지원하지 않는 기능도 많아 RoR과 Play에서 기본적으로 지원하는 기능을 서비스하려면 추가적인 개발이 필요하다.',
         * CURRENT_TIMESTAMP(), 0);
         */
        QuestionDao questionDao = new QuestionDao();
        Question findQuestion = questionDao.findByQuestionId(1);
        assertEquals("자바지기", findQuestion.getWriter());
        assertEquals("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?", findQuestion.getTitle());
        assertEquals("Ruby on Rails(이하 RoR)는 2006년 즈음에 정말 뜨겁게 달아올랐다가 금방 가라 앉았다. Play 프레임워크는 정말 한 순간 잠시 눈에 뜨이다가 사라져 버렸다. RoR과 Play 기반으로 개발을 해보면 정말 생산성이 높으며, 웹 프로그래밍이 재미있기까지 하다. Spring MVC + JPA(Hibernate) 기반으로 진행하면 설정할 부분도 많고, 기본으로 지원하지 않는 기능도 많아 RoR과 Play에서 기본적으로 지원하는 기능을 서비스하려면 추가적인 개발이 필요하다.",
                findQuestion.getContents());
    }

    @Test
    public void findAll() throws Exception {
        QuestionDao questionDao = new QuestionDao();
        List<Question> questions = questionDao.findAll();
        assertEquals(8, questions.size());
    }

    @Test
    public void insert() throws Exception {
        Question question = new Question("JYP", "나는 문어", "꿈을 꾸는 문어~~~");
        QuestionDao questionDao = new QuestionDao();

        questionDao.insert(question);

        List<Question> questions = questionDao.findAll();
        assertEquals(9, questions.size());
    }
}
