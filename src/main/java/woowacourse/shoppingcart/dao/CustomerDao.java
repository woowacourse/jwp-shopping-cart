package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.*;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer save(final Customer customer) {
        final String sql = "insert into customer(email, name, phone, address, password) values (:email, :name, :phone, :address, :password)" +
                " on duplicate key update email = :email, name = :name, phone = :phone, address = :address, password = :password";
        final KeyHolder keyholder = new GeneratedKeyHolder();
        final MapSqlParameterSource query = new MapSqlParameterSource();
        query.addValue("email", customer.getEmail().getValue());
        query.addValue("name", customer.getName().getValue());
        query.addValue("phone", customer.getPhone().getValue());
        query.addValue("address", customer.getAddress().getValue());
        query.addValue("password", customer.getPassword().getValue());
        jdbcTemplate.update(sql, query, keyholder, new String[]{"id"});
        try {
            return new Customer(new CustomerId(Objects.requireNonNull(keyholder.getKey()).longValue()), customer);
        } catch (NullPointerException e) {
            return customer;
        }
    }

    public CustomerId findIdByUserName(final Name name) {
        try {
            final String sql = "SELECT id FROM customer WHERE name = :name";
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("name", name.getValue()), new IdMapper());
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findByEmail(final Email email) {
        final String sql = "select * from customer where email = :email";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("email", email.getValue()), new CustomerMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("존재하지 않는 이메일 입니다.");
        }
    }

    public Customer findById(final CustomerId customerId) {
        final String sql = "select * from customer where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", customerId.getValue()), new CustomerMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCustomerException("존재하지 않는 아이디 입니다.");
        }
    }

    public void delete(final CustomerId customerId) {
        final String sql = "delete from customer where id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", customerId.getValue()));
    }

    public Boolean isDuplication(final Email email) {
        final String sql = "select exists(select * from customer where email = :email)";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("email", email.getValue()), Boolean.class);
    }

    private static class CustomerMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Customer(
                    new CustomerId(rs.getLong("id")),
                    new Email(rs.getString("email")),
                    new Name(rs.getString("name")),
                    new Phone(rs.getString("phone")),
                    new Address(rs.getString("address")),
                    Password.ofEncrypted(rs.getString("password")));
        }
    }

    private static class IdMapper implements RowMapper<CustomerId> {
        @Override
        public CustomerId mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerId(rs.getLong("id"));
        }
    }
}
