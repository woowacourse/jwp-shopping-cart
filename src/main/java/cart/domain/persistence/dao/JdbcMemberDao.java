package cart.domain.persistence.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.persistence.entity.MemberEntity;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<MemberEntity> actorRowMapper = (resultSet, rowNum) -> new MemberEntity(
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
    public long save(final MemberEntity memberEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Optional<MemberEntity> findByEmail(final String email) {
        final String sql = "SELECT member_id, email, password FROM member WHERE email = ?";
        try {
            final MemberEntity memberEntity = jdbcTemplate.queryForObject(sql, actorRowMapper, email);
            return Optional.of(memberEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT member_id, email, password FROM member";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
