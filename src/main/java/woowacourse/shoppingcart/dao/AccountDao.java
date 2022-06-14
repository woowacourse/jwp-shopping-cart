package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.exception.InvalidAccountException;

import java.util.Locale;

@Repository
public class AccountDao {

    private static final RowMapper<Account> ROW_MAPPER = (rs, rowNum) -> {
        String email = rs.getString("email");
        String password = rs.getString("password");
        String nickname = rs.getString("nickname");
        Long id = rs.getLong("id");
        boolean isAdmin = rs.getBoolean("is_admin");
        return new Account(id, email, password, nickname, isAdmin);
    };
    private final JdbcTemplate jdbcTemplate;

    public AccountDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account save(Account account) {
        final String query = "INSERT INTO accounts (email, password, nickname, is_admin) VALUES (?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, account.getEmail());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getNickname());
            preparedStatement.setBoolean(4, account.isAdmin());
            return preparedStatement;
        }, keyHolder);

        long customerId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Account(customerId, account.getEmail(), account.getPassword(), account.getNickname());
    }

    public Optional<Account> findByEmail(String email) {
        try {
            final String query = "SELECT email, password, nickname, id, is_admin FROM accounts WHERE email = ?";
            Account account = jdbcTemplate.queryForObject(query, ROW_MAPPER, email);
            return Optional.of(account);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean existByEmail(String email) {
        final String query = "SELECT EXISTS(SELECT id FROM accounts WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    public void deleteByEmail(String email) {
        final String query = "DELETE FROM accounts WHERE email = ?";
        jdbcTemplate.update(query, email);
    }

    public void update(Account account) {
        final String query = "UPDATE accounts SET password = ?, nickname = ? WHERE id = ?";
        jdbcTemplate.update(query, account.getPassword(), account.getNickname(), account.getId());
    }

}
