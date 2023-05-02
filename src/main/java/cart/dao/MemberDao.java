package cart.dao;

import cart.dao.entity.MemberEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> selectAll() {
        final String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    private RowMapper<MemberEntity> userRowMapper() {
        final RowMapper<MemberEntity> userEntityRowMapper = (resultSet, rowNum) -> new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return userEntityRowMapper;
    }
}
