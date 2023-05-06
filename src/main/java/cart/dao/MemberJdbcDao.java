package cart.dao;

import cart.entity.MemberEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MemberEntity> memberEntityRowMapper = ((rs, rowNum) ->
            new MemberEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
            ));

    public MemberJdbcDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> selectMemberId(final MemberEntity memberEntity) {
        String sql = "select * from member where email = ? and password = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                memberEntityRowMapper,
                memberEntity.getEmail(),
                memberEntity.getPassword()
        ).getId());
    }

    @Override
    public List<MemberEntity> findAll() {
        String sql = "select * from member";
        return jdbcTemplate.query(sql, memberEntityRowMapper);
    }
}
