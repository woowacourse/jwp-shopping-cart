package cart.dao.member;

import cart.dao.Dao;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao implements Dao<MemberEntity> {

    private final RowMapper<MemberEntity> rowMapper = (rs, rowNum) ->
            new MemberEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
            );
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MemberEntity findById(final Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT * FROM member";

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Long save(final MemberEntity memberEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(final MemberEntity memberEntity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int deleteById(final Long id) {
        throw new UnsupportedOperationException();
    }

    public Optional<MemberEntity> findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT * FROM member WHERE email = ? AND password = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, email, password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
