package cart.dao;


import cart.domain.member.Member;
import cart.domain.member.MemberEntity;
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

    public List<MemberEntity> findAll() {
        final String sql = "SELECT id, username, password FROM Member";

        return jdbcTemplate.query(sql, memberEntityRowMapper());
    }

    public boolean isMember(final Member member) {
        final String sql = "SELECT count(*) FROM Member WHERE username = ? AND password = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, member.getUsername(), member.getPassword()) > 0;
    }

    public long findMemberId(final Member member) {
        final String sql = "SELECT id FROM Member WHERE username = ? AND password= ?";

        return jdbcTemplate.queryForObject(sql, memberIdRowMapper(), member.getUsername(), member.getPassword());
    }

    private RowMapper<Long> memberIdRowMapper() {
        return (resultSet, rowNum) -> new Long(resultSet.getLong("id"));
    }

    private RowMapper<MemberEntity> memberEntityRowMapper() {
        return (resultSet, rowNum) -> new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password")
        );
    }
}
