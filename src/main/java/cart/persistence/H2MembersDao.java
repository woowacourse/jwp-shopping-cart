package cart.persistence;

import cart.domain.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class H2MembersDao implements MembersDao {

    private final JdbcTemplate jdbcTemplate;

    public H2MembersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT email, password FROM MEMBER";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Member(
                resultSet.getString("email"),
                resultSet.getString("password")
        ));
    }

    @Override
    public boolean isMemberCertified(String email, String password) {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE email=? AND password=?";

        long userCount = jdbcTemplate.queryForObject(sql, Long.class, email, password);

        return userCount > 0;
    }

    @Override
    public Long findIdByEmail(String email) {
        String sql = "SELECT id FROM MEMBER WHERE email=?";
        return jdbcTemplate.queryForObject(sql, Long.class, email);
    }
}
