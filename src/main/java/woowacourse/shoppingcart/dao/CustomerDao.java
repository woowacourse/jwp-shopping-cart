package woowacourse.shoppingcart.dao;

import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) ->
            new Customer(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    new Password(resultSet.getString("password")),
                    resultSet.getString("name"),
                    resultSet.getString("phone"),
                    resultSet.getString("address")
            );

    public CustomerDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Customer customer) {
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(customer);
        return simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
    }

    public boolean existByEmail(String email) {
        String query = "SELECT EXISTS (SELECT id FROM CUSTOMER WHERE email = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, email);
    }

    public Optional<Long> findIdByEmailAndPassword(String email, String password) {
        String query = "SELECT id FROM CUSTOMER WHERE email = ? AND password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, Long.class, email, password));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public String findNameById(Long customerId) { // TODO : test
        String query = "SELECT name FROM CUSTOMER WHERE id = ?";
        return jdbcTemplate.queryForObject(query, String.class, customerId);
    }

    public Customer findById(Long customerId) {
        String query = "SELECT * FROM CUSTOMER WHERE id = ?";
        return jdbcTemplate.queryForObject(query, customerRowMapper, customerId);
    }

    public boolean existById(Long customerId) {
        String query = "SELECT EXISTS (SELECT id FROM CUSTOMER WHERE id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId);
    }

    public void update(Customer customer) {
        String query = "UPDATE CUSTOMER SET name = (?), password = (?), phone = (?), address = (?) WHERE id = (?)";
        jdbcTemplate.update(query, customer.getName(), customer.getPassword(), customer.getPhone(),
                customer.getAddress(), customer.getId());
    }

    public void deleteById(Long customerId) {
        String query = "DELETE FROM CUSTOMER WHERE id = ?";
        jdbcTemplate.update(query, customerId);
    }
}
