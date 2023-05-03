package cart.dao.member;

import cart.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> Member.of(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public void insert(final Member member) {
        final String sql = "INSERT INTO member(email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    @Override
    @Transactional(readOnly = true)
    public Member findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper, email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }
}