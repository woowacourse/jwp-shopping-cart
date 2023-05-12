package cart.dao;

import cart.domain.Email;
import cart.domain.MemberEntity;
import cart.domain.Password;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MemberDao {

    private static final RowMapper<MemberEntity> MEMBER_ENTITY_ROW_MAPPER = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getLong("id"),
            new Email(resultSet.getString("email")),
            new Password(resultSet.getString("password"))
    );
    private static final String[] GENERATED_ID_COLUMN = {"id"};

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        final String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, MEMBER_ENTITY_ROW_MAPPER);
    }

    public Long insert(final MemberEntity memberEntity) {
        final String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, memberEntity.getEmailAddress());
            preparedStatement.setString(2, memberEntity.getPasswordValue());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteAll() {
        final String sql = "DELETE FROM member";
        jdbcTemplate.update(sql);
    }

    public MemberEntity findById(final Long id) {
        final String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, MEMBER_ENTITY_ROW_MAPPER, id);
    }

    public Optional<MemberEntity> findByEmail(final String email) {
        try {
            final String sql = "SELECT * FROM member WHERE email = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, MEMBER_ENTITY_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
