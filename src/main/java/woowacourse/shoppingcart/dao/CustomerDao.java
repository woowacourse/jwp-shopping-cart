package woowacourse.shoppingcart.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer save(final Customer customer) {
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)";
        final SqlParameterSource query = new BeanPropertySqlParameterSource(customer);
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, query, keyholder, new String[]{"id"});
        return new Customer(Objects.requireNonNull(keyholder.getKey()).longValue(), customer);
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

    public Optional<Customer> findByEmail(final String email) {
        final String sql = "select * from customer where email = :email";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("email", email), new CustomerMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Customer findById(Long id) {
        final String sql = "select * from customer where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), new CustomerMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("존재하지 않는 아이디 입니다.");
        }
    }

    public void edit(Customer customer) {
        final String sql = "update customer set name = :name, phone = :phone, address = :address where id = :id";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(customer));
    }

    public void delete(Long id) {
        final String sql = "delete from customer where id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    private static class CustomerMapper implements RowMapper<Customer> {
        public Customer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    Password.fromEncrypt(rs.getString("password")));
        }
    }
}
