package cart.dao;

import cart.dao.entity.MemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberEntity> findAll() {
        String query = "SELECT * FROM member";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new MemberEntity(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password")));
    }

    public Optional<MemberEntity> findByEmail(String email) {
        String query = "SELECT * FROM member WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNum) ->
                new MemberEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")), email));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }
}
