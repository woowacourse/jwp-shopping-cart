package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
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
        final String query = "SELECT id FROM customer WHERE username = :username";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource("username", userName.toLowerCase(Locale.ROOT));
            return namedParameterJdbcTemplate.queryForObject(query, parameters, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public Optional<Customer> findByLoginId(final String loginId) {
        final String query = "SELECT * FROM customer WHERE loginId = :loginId";
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource("loginId", loginId);
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, parameters, CUSTOMER_ROW_MAPPER));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Customer> save(Customer customer) {
        try {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);
            Long id = insertActor.executeAndReturnKey(parameterSource).longValue();
            return Optional.of(new Customer(id, customer.getLoginId(), customer.getUsername(),
                customer.getPassword()));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }

    public void update(Customer customer) {
        final String query = "UPDATE customer SET username = :username WHERE loginId = :loginId";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(customer);

        try {
            namedParameterJdbcTemplate.update(query, parameterSource);
        } catch (DataAccessException e) {
            throw new DuplicateCustomerException();
        }
    }

    public boolean delete(Long id) {
        final String query = "DELETE FROM customer WHERE id = :id";
        MapSqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.update(query, parameter) != 0;
    }
}
