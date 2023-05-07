package cart.persistence.dao;

import cart.persistence.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<MemberEntity> rowMapper = (resultSet, rowNumber) -> MemberEntity.create(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("phone_number")
    );

    public H2MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public long save(MemberEntity memberEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(memberEntity);
        return this.simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT id, email, password, name, phone_number FROM member";
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        final String sql = "SELECT id, email, password, name, phone_number FROM member WHERE email = ?";
        try {
            MemberEntity memberEntity = this.jdbcTemplate.queryForObject(sql, rowMapper, email);
            return Optional.ofNullable(memberEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
