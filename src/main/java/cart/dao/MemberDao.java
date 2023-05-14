package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "select * from member";
        return jdbcTemplate.query(sql, getMemberMapper());
    }

    public Optional<MemberEntity> findBy(final String email, final String password) {
        final String sql = "select * from member where email = ? and password = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, getMemberMapper(), email, password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<MemberEntity> getMemberMapper() {
        return (rs, cn) -> new MemberEntity(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"));
    }
}
