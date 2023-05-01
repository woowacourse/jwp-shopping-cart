package cart.dao;

import cart.dto.entity.MemberEntity;
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
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, (rs, rownum) ->
                new MemberEntity(rs.getLong(1), rs.getString("email"), rs.getString("password")));
    }
}
