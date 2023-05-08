package cart.dao;

import cart.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> new Member.Builder()
            .email(resultSet.getString("email"))
            .password(resultSet.getString("password"))
            .build();

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sqlForFindAll = "SELECT * FROM Member";

        return jdbcTemplate.query(sqlForFindAll, memberRowMapper);
    }

    public Optional<Member> findByEmail(String email) {
        String sqlForFindByEmail = "SELECT * FROM Member WHERE email = ?";

        List<Member> member = jdbcTemplate.query(sqlForFindByEmail, memberRowMapper, email);

        if (member.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(member.get(0));
    }
}
