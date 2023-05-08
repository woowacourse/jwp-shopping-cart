package cart.persistence.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.persistence.entity.Member;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Member> actorRowMapper = (resultSet, rowNum) -> new Member(
        resultSet.getLong("member_id"),
        resultSet.getString("email"),
        resultSet.getString("password")
    );

    public JdbcMemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("member")
            .usingGeneratedKeyColumns("member_id");
    }

    @Override
    public long save(final Member member) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(member);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<Member> findByEmail(final String email) {
        final String sql = "SELECT member_id, email, password FROM member WHERE email = ?";
        try {
            final Member member = jdbcTemplate.queryForObject(sql, actorRowMapper, email);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        final String sql = "SELECT member_id, email, password FROM member";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
