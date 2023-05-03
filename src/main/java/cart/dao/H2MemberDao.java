package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2MemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public H2MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "select * from member";
        return jdbcTemplate.query(sql, (resultSet, count) -> new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        ));
    }
}
