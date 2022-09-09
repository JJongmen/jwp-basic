package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import next.model.User;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(),
                user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.update("UPDATE USERS SET password=?, name=?, email=? WHERE userId=?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                User user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
                return user;
            }
        };

        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", rowMapper);
    }

    public User findByUserId(String userId) {
        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();
        RowMapper<User> rowMapper = new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs) throws SQLException {
                User user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
                return user;
            }
        };

        return jdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?", rowMapper, userId);
    }
}
