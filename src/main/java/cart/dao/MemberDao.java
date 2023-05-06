package cart.dao;

import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private static final Optional EMPTY = Optional.empty();
    private static final RowMapper<Member> MAPPER = (resultSet, rowNum) -> new Member(
            resultSet.getLong("id"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<Member>> findAll() {
        final String sql = "SELECT * FROM MEMBER";
        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, MAPPER));
        } catch (EmptyResultDataAccessException error) {
            return EMPTY;
        }
    }

    public Optional<Member> findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT * FROM MEMBER WHERE email = ? and password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, MAPPER, email, password).get(0));
        } catch (EmptyResultDataAccessException | DuplicateKeyException error) {
            return EMPTY;
        }
    }

    public Optional<Member> findByEmail(final String email) {
        final String sql = "SELECT * FROM MEMBER WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, MAPPER, email).get(0));
        } catch (EmptyResultDataAccessException | DuplicateKeyException error) {
            return EMPTY;
        }
    }

    public int insert(final Member member) {
        final String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }
}
