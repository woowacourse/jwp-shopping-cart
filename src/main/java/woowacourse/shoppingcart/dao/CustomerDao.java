package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> new Customer(
            resultSet.getLong("id"),
            resultSet.getString("loginid"),
            resultSet.getString("name"),
            resultSet.getString("password")
    );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbc;

    public CustomerDao(final DataSource dataSource) {
        this.simpleJdbc = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(final Customer customer) {
        try {
            SqlParameterSource parameters = new BeanPropertySqlParameterSource(customer);
            return simpleJdbc.executeAndReturnKey(parameters).longValue();
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE name = :username";
            return namedParameterJdbcTemplate.queryForObject(query,
                    Map.of("username", userName.toLowerCase(Locale.ROOT)), Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findById(Long id) {
        try {
            final String query = "SELECT id, loginid, name, password FROM customer WHERE id = :id";
            return namedParameterJdbcTemplate.queryForObject(query, Map.of("id", id), ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}
