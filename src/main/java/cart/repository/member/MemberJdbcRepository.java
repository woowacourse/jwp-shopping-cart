package cart.repository.member;

import cart.domain.member.Member;
import cart.domain.member.MemberId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static cart.repository.member.MemberJdbcRepository.Table.*;

@Repository
public class MemberJdbcRepository implements MemberRepository {
    enum Table {
        TABLE("members"),
        ID("id"),
        NAME("name"),
        EMAIL("email"),
        PASSWORD("password");
        
        private final String name;

        Table(final String name) {
            this.name = name;
        }
    }
    
    private static final int DELETED_COUNT = 1;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE.name)
                .usingGeneratedKeyColumns(ID.name);
    }

    private final RowMapper<Member> rowMapper = (rs, rowNum) -> {
        return new Member(
                MemberId.from(rs.getLong(ID.name)),
                rs.getString(NAME.name),
                rs.getString(EMAIL.name),
                rs.getString(PASSWORD.name)
        );
    };

    @Override
    public MemberId save(final Member member) {
        final long memberId = simpleJdbcInsert.executeAndReturnKey(paramSource(member)).longValue();
        return MemberId.from(memberId);
    }

    private SqlParameterSource paramSource(final Member member) {
        return new MapSqlParameterSource()
                .addValue(NAME.name, member.getName())
                .addValue(EMAIL.name, member.getEmail())
                .addValue(PASSWORD.name, member.getPassword());
    }

    @Override
    public Optional<Member> findByMemberId(final MemberId memberId) {
        final String sql = "SELECT * FROM members WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, memberId.getId()));
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
    public MemberId deleteByMemberId(final MemberId memberId) {
        final String sql = "DELETE FROM members WHERE id = ?";
        final int deleteCount = jdbcTemplate.update(sql, memberId.getId());

        if (deleteCount != DELETED_COUNT) {
            throw new IllegalStateException("회원 삭제 도중 오류가 발생하여 실패하였습니다.");
        }

        return memberId;
    }
}
