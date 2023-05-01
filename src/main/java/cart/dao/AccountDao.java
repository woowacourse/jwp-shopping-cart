package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AccountEntity> rowMapper = (rs, rowNum) ->
            new AccountEntity(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password")
            );

    public AccountDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AccountEntity> findAll() {
        final String sql = "SELECT * FROM ACCOUNT";

        return jdbcTemplate.query(sql, rowMapper);
    }
}
