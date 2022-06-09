package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.datanotfound.CustomerDataNotFoundException;
import woowacourse.shoppingcart.exception.datanotfound.LoginDataNotFoundException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) -> {
        return new Customer(
                resultSet.getLong("id"),
                resultSet.getString("user_id"),
                resultSet.getString("nickname"),
                resultSet.getString("password")
        );
    };

    private static final boolean NOT_WITHDRAWAL = false;
    private static final boolean WITHDRAWAL = true;

    private final JdbcTemplate jdbcTemplate;

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Long save(final Customer customer) {
        String query = "INSERT INTO customer (user_id, nickname, password, withdrawal) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getUserId());
            preparedStatement.setString(2, customer.getNickname());
            preparedStatement.setString(3, customer.getPassword());
            preparedStatement.setBoolean(4, NOT_WITHDRAWAL);
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Customer findByUserId(final String userId) {
        String query = "SELECT id, user_id, nickname, password FROM customer WHERE user_id = ? and withdrawal = ?";
        try {
            return jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, userId, NOT_WITHDRAWAL);
        } catch (EmptyResultDataAccessException exception) {
            throw new LoginDataNotFoundException("아아디 또는 비밀번호를 확인하여주세요.");
        }
    }

    public Customer findById(final Long id) {
        String query = "SELECT id, user_id, nickname, password FROM customer WHERE id = ? and withdrawal = ?";
        try {
            return jdbcTemplate.queryForObject(query, CUSTOMER_ROW_MAPPER, id, NOT_WITHDRAWAL);
        } catch (EmptyResultDataAccessException exception) {
            throw new CustomerDataNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    public void update(final Long id, final String nickname) {
        String query = "UPDATE customer SET nickname = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, nickname, id, NOT_WITHDRAWAL);
    }

    public void updatePassword(final Long id, final String newPassword) {
        String query = "UPDATE customer SET password = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, newPassword, id, NOT_WITHDRAWAL);
    }

    public void delete(final Long id) {
        String query = "UPDATE customer SET withdrawal = ? WHERE id = ? and withdrawal = ?";
        jdbcTemplate.update(query, WITHDRAWAL, id, NOT_WITHDRAWAL);
    }

    public Boolean existCustomerByUserId(final String userId) {
        String query = "SELECT EXISTS (SELECT id FROM customer WHERE user_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, userId);
    }

    public Boolean existCustomerByNickname(final String nickname) {
        String query = "SELECT EXISTS (SELECT id FROM customer WHERE nickname = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, nickname);
    }
}
