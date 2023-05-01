package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AccountEntity> rowMapper = (rs, rowNum) ->
            new AccountEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getLong("cart_id")
            );

    public AccountDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AccountEntity> findAll() {
        final String sql = "SELECT * FROM ACCOUNT";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<AccountEntity> findByEmailAndPassword(final String email, final String password) {
        final String sql = "SELECT * FROM ACCOUNT A WHERE A.email = ? and A.password = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, email, password));
    }
}
