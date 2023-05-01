package cart.repository;

import cart.domain.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJdbcRepository implements MemberRepository {
    private static final int DELETED_COUNT = 1;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("members")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Member> rowMapper = (rs, rowNum) -> {
        return new Member(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    };

    private SqlParameterSource paramSource(final Member member) {
        return new MapSqlParameterSource()
                .addValue("name", member.getName())
                .addValue("email", member.getEmail())
                .addValue("password", member.getPassword());
    }

    @Override
    public long save(final Member member) {
        return simpleJdbcInsert.execute(paramSource(member));
    }

    @Override
    public Optional<Member> findByMemberId(final long memberId) {
        final String sql = "SELECT * FROM members WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, memberId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        final String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public long deleteByMemberId(final long memberId) {
        final String sql = "DELETE FROM members WHERE id = ?";
        final int deleteCount = jdbcTemplate.update(sql, memberId);

        if (deleteCount != DELETED_COUNT) {
            throw new IllegalStateException("회원 삭제 도중 오류가 발생하여 실패하였습니다.");
        }

        return memberId;
    }
}
