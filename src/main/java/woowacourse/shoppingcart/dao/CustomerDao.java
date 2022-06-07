package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dto.AuthorizedCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {
    private static final RowMapper<AuthorizedCustomer> CUSTOMER_MAPPER = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var username = rs.getString("username");
        var email = rs.getString("email");
        var password = rs.getString("password");
        return new AuthorizedCustomer(id, username, email, password);
    };

    private static final String NOT_EXIST_EMAIL = "[ERROR] 존재하지 않는 이메일 입니다.";
    private static final String NOT_EXIST_NAME = "[ERROR] 존재하지 않는 이름입니다.";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :username";
            var namedParameters = new MapSqlParameterSource("username", userName);
            return jdbcTemplate.queryForObject(query, namedParameters, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_NAME);
        }
    }

    public AuthorizedCustomer findCustomerByUserName(final String userName) {
        try {
            final String query = "SELECT * FROM customer WHERE username = :username";
            var namedParameters = new MapSqlParameterSource("username", userName);
            return jdbcTemplate.queryForObject(query, namedParameters, CUSTOMER_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_NAME);
        }
    }

    public boolean isExistName(String username) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE username = :username)";
        var namedParameters = new MapSqlParameterSource("username", username);
        return jdbcTemplate.query(sql, namedParameters, CUSTOMER_MAPPER).size() > 0;
    }

    public boolean isExistEmail(String email) {
        final var sql = "SELECT * FROM customer WHERE exists (SELECT username FROM customer WHERE email = :email)";
        var namedParameters = new MapSqlParameterSource("email", email);
        return jdbcTemplate.query(sql, namedParameters, CUSTOMER_MAPPER).size() > 0;
    }

    public void updatePassword(String name, String newPassword) {
        final String sql = "UPDATE customer SET password = (:password) WHERE username = (:username)";
        var namedParameters = new MapSqlParameterSource("password", newPassword);
        namedParameters.addValue("username", name);
        jdbcTemplate.update(sql, namedParameters);
    }

    public void deleteByName(String name) {
        final var sql = "DELETE FROM customer WHERE username = :username";
        var namedParameters = new MapSqlParameterSource("username", name);
        jdbcTemplate.update(sql, namedParameters);
    }

    public void saveCustomer(String name, String email, String password) {
        final String sql = "INSERT INTO customer (username, email, password) VALUES(:username, :email, :password)";
        var namedParameters = new MapSqlParameterSource("username", name);
        namedParameters.addValue("email", email);
        namedParameters.addValue("password", password);
        jdbcTemplate.update(sql, namedParameters);
    }

    public AuthorizedCustomer findCustomerByEmail(String email) {
        try {
            final String sql = "SELECT * FROM customer WHERE email = :email";
            var namedParameters = new MapSqlParameterSource("email", email);
            return jdbcTemplate.queryForObject(sql, namedParameters, CUSTOMER_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException(NOT_EXIST_EMAIL);
        }
    }
}
