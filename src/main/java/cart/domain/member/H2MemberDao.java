package cart.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao extends MemberDao {

    private static final RowMapper<Member> MEMBER_ROW_MAPPER = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
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
    Optional<Member> findByEmail(final String email) {
        try {
            Member member = jdbcTemplate.queryForObject("SELECT id, email, password FROM MEMBERS WHERE email = ?",
                    MEMBER_ROW_MAPPER, email);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
