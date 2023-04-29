package cart.repository;

import cart.domain.Member;
import cart.entity.MemberEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public void save(Member member) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(member);
        simpleJdbcInsert.execute(sqlParameterSource);
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE email = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT id, email, password FROM MEMBER";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    private RowMapper<MemberEntity> memberRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3));
    }
}
