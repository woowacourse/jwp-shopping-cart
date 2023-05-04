package cart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MemberJdbcDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<MemberEntity> getMemberEntityRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT * FROM MEMBER";

        return jdbcTemplate.query(sql, getMemberEntityRowMapper());
    }

    @Override
    public Optional<MemberEntity> findMemberByEmail(final String email) {
        final String sql = "SELECT * FROM MEMBER WHERE email = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, getMemberEntityRowMapper(), email));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

}
