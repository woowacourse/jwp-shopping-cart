package cart.persistence;

import cart.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2MemberDao implements MemberDao{

    private final JdbcTemplate jdbcTemplate;

    public H2MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT email, password FROM MEMBER";
        return jdbcTemplate.query(sql, (resultSet, rowNum)-> new Member(
                resultSet.getString("email"),
                resultSet.getString("password")
        ));
    }
}
