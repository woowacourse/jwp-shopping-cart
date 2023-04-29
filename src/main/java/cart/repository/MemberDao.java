package cart.repository;

import cart.domain.Member;
import cart.entity.MemberEntity;
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
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public MemberEntity save(Member member) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(member);
        long memberId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return new MemberEntity(memberId, member.getEmail(), member.getPassword());
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM MEMBER WHERE email = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT id, email, password FROM MEMBER";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public Optional<Long> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT id password FROM MEMBER WHERE email = ? AND password = ?";
        try {
            Long memberId = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("id"), email, password);
            return Optional.ofNullable(memberId);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
