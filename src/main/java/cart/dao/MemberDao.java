package cart.dao;

import cart.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> productMapper
            = (resultSet, rowNum) -> new Member(
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    public List<Member> selectAll() {
        String sql = "select email, password from member";
        return jdbcTemplate.query(sql, productMapper);
    }
}
