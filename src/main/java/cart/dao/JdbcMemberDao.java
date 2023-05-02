package cart.dao;

import cart.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberDao implements MemberDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isMemberExists(Member member) {
        String sql = "select exists(select id from member where email = ? and password = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, member.getEmail(), member.getPassword());
    }
}
