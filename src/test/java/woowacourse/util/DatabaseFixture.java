package woowacourse.util;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.domain.Product;

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

    public void save(Product... products) {
        final String sql = "INSERT INTO product(id, name, price, image_url) "
                + "VALUES(:id, :name, :price, :imageUrl)";

        jdbcTemplate.batchUpdate(sql, SqlParameterSourceUtils.createBatch(products));
    }
}
