package cart.dao.member;

import cart.entity.member.Email;
import cart.entity.member.Member;
import cart.entity.member.Password;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
        resultSet.getLong("id"),
        new Email(resultSet.getString("email")),
        new Password(resultSet.getString("password"))
    );

    public MemberDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        String sql = "SELECT id, email, password FROM member WHERE email = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
