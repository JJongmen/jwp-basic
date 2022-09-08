package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql,PreparedStatementSetter pstmtSetter) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmtSetter.setValues(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public List<Object> query(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmtSetter.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            Object object = null;
            while (rs.next()) {
                object = rowMapper.mapRow(rs);
                result.add(object);
            }
            return result;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public Object queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper rowMapper) throws SQLException {
        return query(sql, pstmtSetter, rowMapper).get(0);
    }
}
