package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Repository
public class CustomerDao {

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, rowNum) -> new Customer(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("nickname")
    );

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CustomerDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customer")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Customer customer) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("email", customer.getEmail())
                .addValue("password", customer.getPassword())
                .addValue("nickname", customer.getNickname());

        try {

        } catch (EmptyResultDataAccessException e) {

        }
        return simpleJdbcInsert.executeAndReturnKey(sqlParameter).longValue();
    }

    public Customer findById(Long id) {
        String query = "SELECT id, email, password, nickname FROM customer WHERE id = :id";
        SqlParameterSource nameParameters = new MapSqlParameterSource("id", id);

        return template.queryForObject(query, nameParameters, CUSTOMER_ROW_MAPPER);
    }

    public Long findIdByUserName(final String userName) {
        try {
            final String query = "SELECT id FROM customer WHERE username = :username";
            SqlParameterSource nameParameters = new MapSqlParameterSource("username", userName);

            return template.queryForObject(query, nameParameters, Long.class);
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidCustomerException();
        }
    }

    public boolean existByEmail(String email) {
        String sql = "SELECT exists (SELECT * FROM customer WHERE email = :email)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("email", email);
        Long count = template.queryForObject(sql, nameParameters, Long.class);
        return count != 0L;
    }

    public boolean existByNickname(String nickname) {
        String sql = "SELECT exists (SELECT * FROM customer WHERE nickname = :nickname)";
        MapSqlParameterSource nameParameters = new MapSqlParameterSource("nickname", nickname);
        Long count = template.queryForObject(sql, nameParameters, Long.class);
        return count != 0;
    }
}
