package cart.domain.member.dao;

import cart.domain.member.entity.Member;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> rowMapper = (resultSet, rowNum) -> new Member(
        resultSet.getLong("id"),
        resultSet.getString("email"),
        resultSet.getString("password"),
        resultSet.getTimestamp("created_at").toLocalDateTime(),
        resultSet.getTimestamp("updated_at").toLocalDateTime()
    );

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Member save(final Member member) {
        final String sql = "INSERT INTO MEMBER (email, password, created_at, updated_at) VALUES (?, ?, ?, ?);";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final LocalDateTime now = LocalDateTime.now();
        final Timestamp savedNow = Timestamp.valueOf(now);
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            ps.setTimestamp(3, savedNow);
            ps.setTimestamp(4, savedNow);
            return ps;
        }, keyHolder);
        return new Member(getId(keyHolder), member.getEmail(), member.getPassword(), now, now);
    }

    private long getId(final KeyHolder keyHolder) {
        return Long.parseLong(Objects.requireNonNull(keyHolder.getKeys()).get("id").toString());
    }

    public Optional<Member> findByEmail(final String email) {
        final String sql = "SELECT member.email, member.password, member.created_at, member.updated_at "
            + "FROM member WHERE email = ?";
        try {
            final Member member = jdbcTemplate.queryForObject(sql, rowMapper, email);
            return Optional.ofNullable(member);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Member> findAll() {
        final String sql = "SELECT member.id, member.email, member.password, member.created_at, member.updated_at "
            + "FROM member ORDER BY member.created_at";
        return jdbcTemplate.query(sql, rowMapper);
    }
}
