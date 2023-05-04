package cart.dao;

import cart.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "SELECT id, email, password FROM member";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new MemberEntity(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("password")
                )
        );
    }

    public Optional<MemberEntity> findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT id, email, password FROM member WHERE email = ? and password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                            sql,
                            (rs, rowNum) -> new MemberEntity(
                                    rs.getLong("id"),
                                    rs.getString("email"),
                                    rs.getString("password")
                            ),
                            email,
                            password
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
