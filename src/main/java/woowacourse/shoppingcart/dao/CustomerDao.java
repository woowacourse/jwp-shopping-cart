package woowacourse.shoppingcart.dao;

import java.util.HashMap;
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
import woowacourse.shoppingcart.application.dto.AddressResponse;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
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

    public CustomerResponse findByUserEmail(final Email email) {
        final String query = "SELECT id, email, password, profileImageUrl, name, gender, birthday, contact, address, detailAddress, zoneCode FROM customer WHERE email=:email";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("email", email.getValue()));
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

    public String findPasswordByEmail(final Email email) {
        final String query = "SELECT password FROM customer WHERE email = ?";
        return jdbcTemplate.queryForObject(query, String.class, email.getValue());
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = ?";
            return jdbcTemplate.queryForObject(query, Long.class, userName.toLowerCase(Locale.ROOT));
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public int updateCustomer(final Customer customer) {
        final String query = "UPDATE customer SET password=:password, name=:name, gender=:gender, contact=:contact, address=:address, detailAddress=:detailAddress, zoneCode=:zoneCode WHERE email=:email";
        final Map<String, Object> parameterSource = Map.ofEntries(
                Map.entry("password", customer.getPassword()),
                Map.entry("name", customer.getName()),
                Map.entry("gender", customer.getGender()),
                Map.entry("contact", customer.getContact()),
                Map.entry("address", customer.getAddress()),
                Map.entry("detailAddress", customer.getDetailAddress()),
                Map.entry("zoneCode", customer.getZoneCode()),
                Map.entry("email", customer.getEmail())
        );
        final SqlParameterSource parameter = new MapSqlParameterSource(parameterSource);
        return namedJdbcTemplate.update(query, parameter);
    }
}
