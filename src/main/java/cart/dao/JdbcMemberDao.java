package cart.dao;

import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    @Override
    public long insert(final MemberEntity memberEntity) {
        final String sql = "INSERT INTO member(email, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, memberEntity.getEmail(), memberEntity.getPassword());
    }
}
