package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberJdbcDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MemberEntity> memberEntityRowMapper = ((rs, rowNum) ->
            new MemberEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
            ));

    public MemberJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MemberEntity> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, memberEntityRowMapper);
    }
}
