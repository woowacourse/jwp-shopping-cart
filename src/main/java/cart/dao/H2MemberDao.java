package cart.dao;

import cart.domain.Email;
import cart.domain.Member;
import cart.domain.Password;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao implements ReadOnlyDao<Member, Email> {

    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            new Email(resultSet.getString("email")),
            new Password(resultSet.getString("password"))
    );
    private final JdbcTemplate jdbcTemplate;

    public H2MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT id, email, password FROM members", MEMBER_ROW_MAPPER);
    }

    @Override
    public Optional<Member> findByUnique(final Email unique) {
        return Optional.empty();
    }
}
