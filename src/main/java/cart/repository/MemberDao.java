package cart.repository;

import cart.entity.MemberEntity;
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
        String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    public RowMapper<MemberEntity> memberRowMapper() {
        return (rs, rowNum) -> MemberEntity.builder()
                .id(rs.getLong(1))
                .email(rs.getString(2))
                .password(rs.getString(3))
                .build();
    }
}
