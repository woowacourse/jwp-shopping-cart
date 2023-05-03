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

        return jdbcTemplate.query(
                sqlForSelectAll,
                (resultSet, rowNum) -> new Member.Builder()
                        .email(Email.from(resultSet.getString("email")))
                        .password(Password.from(resultSet.getString("password")))
                        .build());
    }

    public boolean existsByEmail(String email) {
        String sqlForExistsByEmail = "SELECT * FROM Member WHERE = ?";
        Member member = jdbcTemplate.queryForObject(sqlForExistsByEmail, rowMapper, email);
        return member == null;
    }

}
