package cart.dao.member;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNumber) -> {
        Long id = resultSet.getLong("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        return new Member(id, new Email(email), new Password(password));
    };

    public JdbcMemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        final String sql = "select * from member";
        return jdbcTemplate.query(sql, memberRowMapper);
    }
}
