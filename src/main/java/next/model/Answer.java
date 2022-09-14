package next.model;

import java.sql.Timestamp;

public class Answer {
    private long answerId;
    private String writer;
    private String contents;
    private Timestamp createdDate;
    private long questionId;

    public Answer(String writer, String contents, long questionId) {
        this.writer = writer;
        this.contents = contents;
        this.questionId = questionId;
    }

    public Answer(long answerId, String writer, String contents, Timestamp createdDate, long questionId) {
        this(writer, contents, questionId);
        this.answerId = answerId;
        this.createdDate = createdDate;
    }

    public long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public long getQuestionId() {
        return questionId;
    }
}
