package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuestionDao {
    public Question findByQuestionId(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT * FROM QUESTIONS WHERE questionId = ?";
        return jdbcTemplate.queryForObject(sql, rs -> new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer")),
                questionId
        );
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT * FROM QUESTIONS";
        return jdbcTemplate.query(sql, rs -> new Question(
                        rs.getLong("questionId"),
                        rs.getString("writer"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfAnswer"))
        );
    }

    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,question.getWriter(), question.getTitle(), question.getContents(), new Timestamp(System.currentTimeMillis()), 0);
    }
}
