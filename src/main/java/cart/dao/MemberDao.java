package cart.dao;

import cart.auth.AuthenticationException;
import cart.domain.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class MemberDao {
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Member> rowMapper;

    public MemberDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.simpleJdbcInsert = initSimpleJdbcInsert(namedParameterJdbcTemplate);
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = initRowMapper();
    }

    private SimpleJdbcInsert initSimpleJdbcInsert(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<Member> initRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

    public long save(final Member member) {
        final SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(member);
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<Member> findAll() {
        final String sql = "SELECT * FROM member";
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    public Member findByEmail(final String email) {
        final String sql = "SELECT * FROM member WHERE email=:email";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("email", email);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, rowMapper);
        } catch (EmptyResultDataAccessException exception) {
            throw new AuthenticationException();
        }
    }

    public void deleteAll() {
        final String sql = "DELETE FROM member";
        namedParameterJdbcTemplate.update(sql, Collections.emptyMap());
    }
}
