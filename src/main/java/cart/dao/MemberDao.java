package cart.dao;

import cart.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@AllArgsConstructor
public class MemberDao {

    private static final RowMapper<MemberEntity> memberRowMapper = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("address"),
            resultSet.getInt("age")
    );

    private final JdbcTemplate jdbcTemplate;

    public List<MemberEntity> findAll() {
        String sql = "select id, email, password, name, address, age from Member";

        return jdbcTemplate.query(sql, memberRowMapper);
    }
}
