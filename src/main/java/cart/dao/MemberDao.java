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

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String AGE = "age";
    private final static String ALL_COLUMNS = String.join(", ", ID, EMAIL, PASSWORD, NAME, ADDRESS, AGE);

    private static final RowMapper<MemberEntity> memberRowMapper = (resultSet, rowNum) -> new MemberEntity(
            resultSet.getLong(ID),
            resultSet.getString(EMAIL),
            resultSet.getString(PASSWORD),
            resultSet.getString(NAME),
            resultSet.getString(ADDRESS),
            resultSet.getInt(AGE)
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
