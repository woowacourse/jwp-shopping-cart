package cart.dao;

import cart.entity.Member;
import cart.vo.Email;
import cart.vo.Password;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member.Builder()
            .email(Email.from(resultSet.getString("email")))
            .password(Password.from(resultSet.getString("password")))
            .build();
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> selectAll() {
        String sqlForSelectAll = "SELECT * FROM Member";

        return jdbcTemplate.query(sqlForSelectAll, rowMapper);
    }

    public Member findByEmail(String email) {
        String sqlForFindByEmail = "SELECT * FROM Member WHERE email = ?";
        return jdbcTemplate.queryForObject(sqlForFindByEmail, rowMapper, email);
    }

}
