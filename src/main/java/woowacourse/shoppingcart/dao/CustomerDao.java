package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.util.Locale;
import java.util.Objects;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String sql = "SELECT id FROM customer WHERE name = :name";
            final SqlParameterSource query = new MapSqlParameterSource("name", userName.toLowerCase(Locale.ROOT));
            return jdbcTemplate.queryForObject(sql, query, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer save(final Customer customer) {
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        final SqlParameterSource query = new BeanPropertySqlParameterSource(customer);
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, query, keyholder, new String[]{"id"});
        return new Customer(Objects.requireNonNull(keyholder.getKey()).longValue(), customer);
    }
}
