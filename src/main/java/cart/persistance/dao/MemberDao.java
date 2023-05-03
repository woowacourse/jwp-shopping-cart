package cart.persistance.dao;

import cart.persistance.entity.user.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    public static final RowMapper<Member> mapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"));

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, mapper);
    }

    public Member findByEmail(final String email) {
        final String sql = "SELECT * FROM member WHERE email = ?";
        final List<Member> result = jdbcTemplate.query(sql, mapper, email);

        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
