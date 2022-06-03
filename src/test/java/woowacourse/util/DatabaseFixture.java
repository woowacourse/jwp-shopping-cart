package woowacourse.util;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.User;
import woowacourse.shoppingcart.domain.Customer;

@Repository
public class DatabaseFixture {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DatabaseFixture(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Customer customer) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, :nickname, :age)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(customer);

        jdbcTemplate.update(sql, params);
    }

    public void save(User user) {
        final String sql = "INSERT INTO customer(username, password, nickname, age) "
                + "VALUES(:username, :password, '닉네임', 15)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);

        jdbcTemplate.update(sql, params);
    }
}
