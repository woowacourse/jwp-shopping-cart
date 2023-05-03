package cart.dao;


import cart.domain.member.Member;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        final String sql = "SELECT id, username, password FROM Member";
        return jdbcTemplate.query(sql, getMemberRowMapper());
    }

    public boolean isMember(final Member member) {
        final String sql = "SELECT count(*) FROM Member WHERE username = ? AND password = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, member.getUsername(), member.getPassword()) > 0;
    }

    private RowMapper<Member> getMemberRowMapper() {
        return (resultSet, rowNum) -> new Member(resultSet.getLong("id"),
                resultSet.getString("username"), resultSet.getString("password"));
    }
}
