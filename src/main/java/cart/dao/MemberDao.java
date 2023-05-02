package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(final MemberEntity entity) {
        String sql = "INSERT INTO MEMEBER (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, entity.getEmail(), entity.getPassword());
    }

    public List<MemberEntity> findAll() {
        String sql = "SELECT * FROM MEMBER";
        return jdbcTemplate.query(sql, (rs, rowNum)
                        -> new MemberEntity(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                )
        );
    }

}
