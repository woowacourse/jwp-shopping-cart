package cart.repository.dao;

import cart.repository.entity.MemberEntity;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMemberDao implements Dao<MemberEntity> {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MemberEntity> actorRowMapper = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getLong("member_id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    public JdbcMemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final MemberEntity memberEntity) {
        throw new UnsupportedOperationException("지원하지 않는 메서드입니다.");
    }

    @Override
    public MemberEntity findById(final Long id) {
        throw new UnsupportedOperationException("지원하지 않는 메서드입니다.");
    }

    @Override
    public List<MemberEntity> findAll() {
        final String sql = "SELECT member_id, name, email, password FROM member";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(final MemberEntity memberEntity) {
        throw new UnsupportedOperationException("지원하지 않는 메서드입니다.");
    }

    @Override
    public int delete(final Long id) {
        throw new UnsupportedOperationException("지원하지 않는 메서드입니다.");
    }

    public MemberEntity findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT member_id, name, email, password FROM member WHERE email = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, email, password);
    }
}
