package cart.dao;

import cart.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    void save(Member member) {
        String sql = "insert into MEMBER (email, password) values (?,?)";

        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    public List<Member> findAll() {
        String sql = "select * from MEMBER";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Member(rs.getLong("member_id"),
                rs.getString("email"),
                rs.getString("password")));
    }
}
