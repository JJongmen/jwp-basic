package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate<E> {

    public void update(String sql,PreparedStatementSetter pstmtSetter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmtSetter.setValues(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public List<E> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper<E> rowMapper) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmtSetter.setValues(pstmt);
            try (ResultSet rs = pstmt.executeQuery();) {
                List<E> result = new ArrayList<>();
                E object = null;
                while (rs.next()) {
                    object = rowMapper.mapRow(rs);
                    result.add(object);
                }
                return result;
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public E queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<E> rowMapper) {
        return query(sql, pstmtSetter, rowMapper).get(0);
    }
}
