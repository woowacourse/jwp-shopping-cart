package cart.dao;

import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<MemberEntity>> findAll() {
        final String sql = "SELECT * FROM MEMBER";
        try {
            return Optional.ofNullable(jdbcTemplate.query(sql, memberRowMapper()));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }

    private RowMapper<MemberEntity> memberRowMapper() {
        final RowMapper<MemberEntity> userEntityRowMapper = (resultSet, rowNum) -> new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
        return userEntityRowMapper;
    }

    public Optional<MemberEntity> findMember(final Member member) {
        final String sql = "SELECT * FROM MEMBER WHERE email = ? and password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberRowMapper(), member.getEmail(), member.getPassword()));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }

    public Optional<MemberEntity> findMemberByEmail(final String email) {
        final String sql = "SELECT * FROM MEMBER WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberRowMapper(), email));
        } catch (EmptyResultDataAccessException error) {
            return Optional.empty();
        }
    }

    public int insert(final Member member) {
        final String sql = "INSERT INTO MEMBER (email, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
    }
}
