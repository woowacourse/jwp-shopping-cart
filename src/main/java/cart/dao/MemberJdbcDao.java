package cart.dao;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJdbcDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            new Email(resultSet.getString("email")),
            new Password(resultSet.getString("password"))
    );

    public MemberJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Member> findByEmail(final Email email) {
        final String sql = "SELECT * FROM member WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, email.email()));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
