package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = ((rs, rowNum) ->
            Customer.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .phoneNumber(rs.getString("phone_number"))
                    .address(rs.getString("address"))
                    .build()
    );

    public CustomerDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("존재하지 않는 유저입니다.");
        }
    }

    public Customer findByUsername(final String username) {
        try {
            final String query = "SELECT id, username, password, phone_number, address FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, customerRowMapper, username);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("존재하지 않는 유저입니다.");
        }
    }

    public Long save(final Customer customer) {
        final String query = "INSERT INTO customer (username, password, phone_number, address) VALUES (?, ?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, customer.getUsername());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getAddress());
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean existCustomerByUsername(final String username) {
        final String query = "SELECT EXISTS (SELECT id FROM customer WHERE username = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, username);
    }

    public int update(final Customer customer) {
        final String query = "UPDATE customer SET phone_number = ?, address = ? WHERE username = ?";
        int rowCount = jdbcTemplate
                .update(query, customer.getPhoneNumber(), customer.getAddress(), customer.getUsername());
        if (rowCount == 0) {
            throw new InvalidCustomerException("유저 업데이트에 실패했습니다.");
        }
        return rowCount;
    }

    public int updatePassword(final String username, final Password password) {
        final String query = "UPDATE customer SET password = ? WHERE username = ?";
        int rowCount = jdbcTemplate.update(query, password.getPassword(), username);
        if (rowCount == 0) {
            throw new InvalidCustomerException("패스워드 변경에 실패했습니다.");
        }
        return rowCount;
    }

    public int deleteByUsername(final String username) {
        final String query = "DELETE FROM customer WHERE username = ?";
        return jdbcTemplate.update(query, username);
    }
}
