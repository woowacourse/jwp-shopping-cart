package cart.product.dao;

import cart.product.domain.Email;
import cart.product.domain.Member;
import cart.product.domain.Password;
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
