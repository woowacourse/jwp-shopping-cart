package cart.repository;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "select * from member";
        return jdbcTemplate.query(sql, getCustomerMapper());
    }

    private RowMapper<MemberEntity> getCustomerMapper() {
        return (rs, cn) -> new MemberEntity(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
