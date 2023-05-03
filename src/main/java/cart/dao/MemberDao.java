package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemberDao {

    private static final RowMapper<MemberEntity> rowMapper =
            (rs, rowNum) -> new MemberEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3)
            );

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(final MemberEntity entity) {
        String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
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

    public Optional<MemberEntity> findByEmail(String email) {
        String sql = "SELECT * FROM MEMBER WHERE email = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, email));
    }
}
