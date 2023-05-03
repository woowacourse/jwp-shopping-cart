package cart.dao;

import cart.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDao implements Dao<Member>{
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Member");
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> productMapper
            = (resultSet, rowNum) -> new Member(
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public int insert(Member member) {
        return 0;
    }

    @Override
    public List<Member> selectAll() {
        String sql = "select email, password from member";
        return jdbcTemplate.query(sql, productMapper);
    }

    @Override
    public int update(Member member) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

}
