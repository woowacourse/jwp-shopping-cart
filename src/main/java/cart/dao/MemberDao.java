package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new MemberEntity(
                rs.getLong("id"),
                rs.getString("email"),
                rs.getString("password")
        ));
    }
}
