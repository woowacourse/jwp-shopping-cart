package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CustomerDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long createCustomer(final Customer customer) {
        final BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(customer);
        return simpleInsert.executeAndReturnKey(parameter).longValue();
    }

    public CustomerDto findCustomerByEmail(final Email email) {
        final String query = "SELECT id, email, password, profileImageUrl, name, gender, birthday, contact, address, detailAddress, zoneCode FROM customer WHERE email=:email";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return CreateCustomerDto(query, parameter);
    }

    public Long findIdByEmail(final Email email) {
        final String query = "SELECT id FROM customer WHERE email = ?";
        return jdbcTemplate.queryForObject(query, Long.class, email.getValue());
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean hasEmail(String email) {
        String sql = "SELECT EXISTS(SELECT * FROM CUSTOMER WHERE email = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, email));
    }

    public int updateCustomer(final Long customerId, final Customer customer) {
        final String query = "UPDATE customer SET password=:password, name=:name, gender=:gender, contact=:contact, address=:address, detailAddress=:detailAddress, zoneCode=:zoneCode WHERE id=:customerId";
        final Map<String, Object> parameterSource = Map.ofEntries(
                Map.entry("password", customer.getPassword()),
                Map.entry("name", customer.getName()),
                Map.entry("gender", customer.getGender()),
                Map.entry("contact", customer.getContact()),
                Map.entry("address", customer.getAddress()),
                Map.entry("detailAddress", customer.getDetailAddress()),
                Map.entry("zoneCode", customer.getZoneCode()),
                Map.entry("email", customer.getEmail()),
                Map.entry("customerId", customerId)

        );
        final SqlParameterSource parameter = new MapSqlParameterSource(parameterSource);
        return namedJdbcTemplate.update(query, parameter);
    }

    public int deleteCustomer(final Long customerId) {
        final String query = "DELETE FROM customer WHERE id=:customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return namedJdbcTemplate.update(query, parameter);
    }

    public CustomerResponse findByUserEmail(final Email email) {
        final String query = "SELECT id, email, password, profileImageUrl, name, gender, birthday, contact, address, detailAddress, zoneCode FROM customer WHERE email=:email";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return CreateCustomerResponse(query, parameter);
    }

    public CustomerResponse findByCustomerId(Long customerId) {
        final String query = "SELECT id, email, password, profileImageUrl, name, gender, birthday, contact, address, detailAddress, zoneCode FROM customer WHERE id=:customerId";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("customerId", customerId));
        return CreateCustomerResponse(query, parameter);
    }

    private CustomerResponse CreateCustomerResponse(String query, SqlParameterSource parameter) {
        return namedJdbcTemplate.queryForObject(query, parameter, (resultSet, rowNum) -> {
            return new CustomerResponse(resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("profileImageUrl"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("birthday"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("detailAddress"),
                    resultSet.getString("zoneCode")
            );
        });
    }

    private CustomerDto CreateCustomerDto(String query, SqlParameterSource parameter) {
        return namedJdbcTemplate.queryForObject(query, parameter, (resultSet, rowNum) -> {
            return new CustomerDto(resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("profileImageUrl"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("birthday"),
                    resultSet.getString("contact"),
                    resultSet.getString("address"),
                    resultSet.getString("detailAddress"),
                    resultSet.getString("zoneCode")
            );
        });
    }
}
