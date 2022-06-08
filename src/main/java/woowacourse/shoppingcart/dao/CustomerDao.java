package woowacourse.shoppingcart.dao;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.domain.address.FullAddress;
import woowacourse.shoppingcart.domain.customer.Birthday;
import woowacourse.shoppingcart.domain.customer.Contact;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.domain.customer.Gender;
import woowacourse.shoppingcart.domain.customer.Name;
import woowacourse.shoppingcart.domain.customer.password.PasswordFactory;
import woowacourse.shoppingcart.domain.customer.password.PasswordType;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Component
public class CustomerDao {

    private final SimpleJdbcInsert simpleInsert;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CustomerDao(final DataSource dataSource) {
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("customer")
                .usingGeneratedKeyColumns("id");
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long createCustomer(final Customer customer) {
        final BeanPropertySqlParameterSource parameter = new BeanPropertySqlParameterSource(customer);
        return simpleInsert.executeAndReturnKey(parameter).longValue();
    }

    public Customer findByUserEmail(final Email email) {
        final String query = "SELECT id, email, password, profileImageUrl, name, gender, birthday, contact, address, detailAddress, zoneCode FROM customer WHERE email=:email";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return namedJdbcTemplate.queryForObject(query, parameter, (resultSet, rowNum) -> {
            return getCustomer(resultSet.getLong("id"),
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

    private Customer getCustomer(Long id, String email, String password, String profileImageUrl, String name,
                                 String gender, String birthDay, String contact, String address,
                                 String detailAddress, String zoneCode) {
        return new Customer(id, new Email(email), PasswordFactory.of(PasswordType.EXISTED, password),
                profileImageUrl, new Name(name), Gender.form(gender),
                new Birthday(birthDay), new Contact(contact),
                new FullAddress(address, detailAddress, zoneCode));
    }

    public String findPasswordByEmail(final Email email) {
        final String query = "SELECT password FROM customer WHERE email = :email";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return namedJdbcTemplate.queryForObject(query, params, String.class);
    }

    public Email findEmail(final Email email) {
        final String query = "SELECT email FROM customer WHERE email=:email";
        final SqlParameterSource params = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return namedJdbcTemplate.queryForObject(query, params, Email.class);
    }

    public Long findIdByUserEmail(final Email email) {
        try {
            final String query = "SELECT id FROM customer WHERE email = :email";
            final SqlParameterSource params = new MapSqlParameterSource(Map.of("email", email.getValue()));
            return namedJdbcTemplate.queryForObject(query, params, Long.class);
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

    public int deleteCustomer(final Email email) {
        final String query = "DELETE FROM customer WHERE email=:email";
        final SqlParameterSource parameter = new MapSqlParameterSource(Map.of("email", email.getValue()));
        return namedJdbcTemplate.update(query, parameter);
    }
}
