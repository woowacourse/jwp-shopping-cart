package woowacourse.shoppingcart.dao;

import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE account = :username";
            SqlParameterSource parameterSource = new MapSqlParameterSource("username",
                    userName.toLowerCase(Locale.ROOT));
            return jdbcTemplate.queryForObject(query, parameterSource, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public void save(CustomerEntity customerEntity) {
        String sql = "INSERT INTO customer (account, nickname, password, address, phone_number) "
                + "VALUES (:account, :nickname, :password, :address, :phoneNumber)";
        SqlParameterSource source = new BeanPropertySqlParameterSource(customerEntity);
        jdbcTemplate.update(sql, source);
    }
}
