package cart.dao;

import cart.entity.Member;
import cart.exception.ServiceIllegalArgumentException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberDao implements MemberDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Member member) {
        String sql = "insert into member(email, password) values(?, ?)";
        if (isEmailExists(member.getEmail())) {
            throw new ServiceIllegalArgumentException("이메일이 중복되었습니다.");
        }

        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    @Override
    public boolean isEmailExists(String email) {
        String sql = "select exists(select id from member where email = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    @Override
    public boolean isMemberExists(Member member) {
        String sql = "select exists(select id from member where email = ? and password = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, member.getEmail(), member.getPassword());
    }
}
