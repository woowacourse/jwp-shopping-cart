package cart.dao;

import cart.domain.Member;
import cart.exception.custom.ResourceNotFoundException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password"));

    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Member> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Member findByEmail(String email) {
        String sql = "select * from member where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("해당하는 email의 member가 존재하지 않습니다.");
        }
    }
}
