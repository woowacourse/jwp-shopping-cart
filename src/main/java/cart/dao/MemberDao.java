package cart.dao;

import cart.dto.AuthInfo;
import cart.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MemberDao {

    private final static String ALL_COLUMNS = "id, email, password, name, address, age";

    private static final RowMapper<MemberEntity> memberRowMapper = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getInt("id"),
            resultSet.getString("email"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getString("address"),
            resultSet.getInt("age")
    );

    private final JdbcTemplate jdbcTemplate;

    public List<MemberEntity> findAll() {
        String sql = "select " + ALL_COLUMNS + " from Member";

        return jdbcTemplate.query(sql, memberRowMapper);
    }

    public Optional<MemberEntity> findByAuthInfo(final AuthInfo authInfo) {
        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();

        String sql = "select " + ALL_COLUMNS + " from Member where email = ? and password = ?";

        try {
            final MemberEntity foundMember = jdbcTemplate.queryForObject(sql, memberRowMapper, email, password);
            return Optional.ofNullable(foundMember);
        } catch (final DataAccessException e) {
            return Optional.empty();
        }
    }
}
