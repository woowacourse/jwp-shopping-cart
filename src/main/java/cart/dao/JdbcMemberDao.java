package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcMemberDao implements MemberDao {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MemberEntity> customerMapper
            = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    public JdbcMemberDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MemberEntity> selectAllMembers() {
        String sql = "select id, email, password from member";
        return jdbcTemplate.query(sql, customerMapper);
    }

    @Override
    public int findMemberId(String email, String password) {
        String sql = "select id " +
                "from member " +
                "where member.email = ? and member.password = ?";
        return jdbcTemplate.queryForObject(sql, int.class, email, password);
    }

}
