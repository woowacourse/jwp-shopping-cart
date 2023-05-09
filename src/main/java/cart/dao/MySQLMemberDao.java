package cart.dao;

import cart.dao.entity.MemberEntity;
import cart.domain.Member;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLMemberDao implements MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MySQLMemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(Member member) {
        String query = "INSERT INTO member (email, password) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, member.getEmail());
            ps.setString(2, member.getPassword());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<MemberEntity> findAll() {
        String query = "SELECT * FROM member";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")));
    }

    @Override
    public Optional<Long> findIdByMember(Member member) {
        String query = "SELECT id FROM member WHERE email = ? AND password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query,
                (resultSet, rowNum) -> resultSet.getLong("id"),
                member.getEmail(), member.getPassword()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
