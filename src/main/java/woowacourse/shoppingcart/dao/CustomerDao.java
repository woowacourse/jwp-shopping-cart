package woowacourse.shoppingcart.dao;

import java.util.Locale;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (resultSet, rowNum) ->
            new Customer(resultSet.getLong("id"),
                    resultSet.getString("loginId"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public CustomerDao(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :username";
            MapSqlParameterSource parameters = new MapSqlParameterSource("username", userName.toLowerCase(Locale.ROOT));
            return namedParameterJdbcTemplate.queryForObject(query, parameters, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Customer findIdByLoginId(final String loginId) {
        try {
            final String query = "SELECT * FROM customer WHERE loginId = :loginId";
            MapSqlParameterSource parameters = new MapSqlParameterSource("loginId", loginId);
            return namedParameterJdbcTemplate.queryForObject(query, parameters, CUSTOMER_ROW_MAPPER);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existByLoginId(final String loginId) {
        final String query = "SELECT EXISTS (SELECT 1 FROM customer WHERE loginId = :loginId)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("loginId", loginId);

        return namedParameterJdbcTemplate.queryForObject(query, parameters, Integer.class) != 0;
    }

    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
        Long id = insertActor.executeAndReturnKey(parameterSource).longValue();
        return new Customer(id, customer.getLoginId(), customer.getUsername(), customer.getPassword());
    }

    public boolean checkValidLogin(String loginId, String password) {
        final String query = "SELECT EXISTS (SELECT 1 FROM customer WHERE loginId = :loginId and password = :password)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("loginId", loginId);
        parameters.addValue("password", password);
        return namedParameterJdbcTemplate.queryForObject(query, parameters, Integer.class) != 0;
    }
}
