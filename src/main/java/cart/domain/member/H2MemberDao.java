package cart.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao extends MemberDao {

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
    Optional<Member> findByEmail(final Email email) {
        return Optional.empty();
    }
}
