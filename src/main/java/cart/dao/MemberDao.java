package cart.dao;

import cart.domain.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBERS";
        return jdbcTemplate.query(sql, getMemberEntityRowMapper());
    }

    private static RowMapper<MemberEntity> getMemberEntityRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                rs.getString("email"),
                rs.getString("password")

        );
    }
}
