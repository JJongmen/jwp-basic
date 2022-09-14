package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.sql.Timestamp;
import java.util.List;

public class AnswerDao {
    public Answer findByAnswerId(long answerId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT * FROM ANSWERS WHERE answerId = ?";
        return template.queryForObject(sql, rs -> new Answer(
                rs.getLong("answerId"),
                rs.getString("writer"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getLong("questionId")),
                answerId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT * FROM ANSWERS WHERE questionId = ?";
        return template.query(sql, rs -> new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getLong("questionId")),
                        questionId);
    }

    public void insert(Answer answer) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        template.update(sql, answer.getWriter(), answer.getContents(), new Timestamp(System.currentTimeMillis()), answer.getQuestionId());
    }
}
