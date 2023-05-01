package cart.domain.settings.persistence.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.domain.settings.persistence.entity.MemberEntity;

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
    public Long save(final MemberEntity memberEntity) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public MemberEntity findByEmail(final String email) {
        final String sql = "SELECT member_id, email, password FROM member WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, email);
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT member_id, email, password FROM member";
        return jdbcTemplate.query(sql, actorRowMapper);
    }
}
