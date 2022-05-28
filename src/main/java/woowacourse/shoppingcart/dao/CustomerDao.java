package woowacourse.shoppingcart.dao;

import java.util.Locale;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbc;

    public CustomerDao(final DataSource dataSource) {
        this.simpleJdbc = new SimpleJdbcInsert(dataSource)
                .withTableName("CUSTOMER")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(final Customer customer) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(customer);
        return simpleJdbc.executeAndReturnKey(parameters).longValue();
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :username";
            return namedParameterJdbcTemplate.queryForObject(query,
                    Map.of("username", userName.toLowerCase(Locale.ROOT)), Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }
}
