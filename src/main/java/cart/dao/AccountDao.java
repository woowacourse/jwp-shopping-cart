package cart.dao;


import cart.domain.account.Account;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public AccountDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Account> fetchAll() {
        final String sql = "SELECT id, username, password FROM Account";
        return jdbcTemplate.query(sql, getAccountRowMapper());
    }

    public boolean isMember(final Account account) {
        final String sql = "SELECT count(*) FROM Account WHERE username = ? AND password = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, account.getUsername(), account.getPassword()) > 0;
    }

    private RowMapper<Account> getAccountRowMapper() {
        return (resultSet, rowNum) -> new Account(resultSet.getLong("id"),
                resultSet.getString("username"), resultSet.getString("password"));
    }
}
