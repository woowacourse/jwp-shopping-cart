package cart.dao;

import cart.entity.Member;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sqlForFindAll = "SELECT * FROM Member";

        return jdbcTemplate.query(sqlForFindAll, (resultSet, rowNum) -> new Member.Builder()
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build()
        );
    }
}
