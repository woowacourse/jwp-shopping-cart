package cart.dao;

import cart.entity.AuthMember;
import cart.entity.Member;
import cart.exception.ServiceIllegalArgumentException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemberDao implements MemberDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "select id, email, password from member";
        return jdbcTemplate.query(sql, mapRow());
    }

    private RowMapper<Member> mapRow() {
        return (rs, rowNum) -> {
            Long id = rs.getLong(1);
            String email = rs.getString(2);
            String password = rs.getString(3);

            return new Member(id, email, password);
        };
    }

    @Override
    public void save(AuthMember authMember) {
        String sql = "insert into member(email, password) values(?, ?)";
        if (isEmailExists(authMember.getEmail())) {
            throw new ServiceIllegalArgumentException("이메일이 중복되었습니다.");
        }

        jdbcTemplate.update(sql, authMember.getEmail(), authMember.getPassword());
    }

    @Override
    public boolean isEmailExists(String email) {
        String sql = "select exists(select id from member where email = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    @Override
    public boolean isMemberExists(AuthMember authMember) {
        String sql = "select exists(select id from member where email = ? and password = ?)";

        return jdbcTemplate.queryForObject(sql, Boolean.class, authMember.getEmail(), authMember.getPassword());
    }
}
