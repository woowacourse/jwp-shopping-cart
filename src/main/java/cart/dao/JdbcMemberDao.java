package cart.dao;

import cart.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) ->
            new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("name")
            );

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long insert(final Member member) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
        return insertActor.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public List<Member> findAll() {
        final String sql = "SELECT * FROM member ORDER BY id ASC";
        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        final String sql = "SELECT * FROM member WHERE email = ? AND password = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper, email, password);
            return Optional.of(member);
        } catch (IncorrectResultSizeDataAccessException exception) {
            return Optional.empty();
        }
    }
}
