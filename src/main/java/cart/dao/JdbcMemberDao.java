package cart.dao;

import cart.domain.entity.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> memberEntityRowMapper = (resultSet, rowNum) -> Member.of(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public List<Member> selectAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberEntityRowMapper);
    }

    @Override
    public long insert(final Member member) {
        final String sql = "INSERT INTO member(email, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }

    @Override
    public Member selectByEmailAndPassword(final Member member) {
        final String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, memberEntityRowMapper, member.getEmail(), member.getPassword());
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }

    @Override
    public Member selectByEmail(final String email) {
        final String sql = "SELECT * FROM member WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, memberEntityRowMapper,email);
        } catch (EmptyResultDataAccessException exception) {
            return null;
        }
    }
}
