package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class JdbcMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<MemberEntity> memberEntityRowMapper = (resultSet, rowNum) -> MemberEntity.of(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public List<MemberEntity> selectAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, memberEntityRowMapper);
    }
}
