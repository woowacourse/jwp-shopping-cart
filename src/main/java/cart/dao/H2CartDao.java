package cart.dao;

import cart.domain.cart.CardDao;
import cart.domain.cart.Cart;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class H2CartDao implements CardDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long addProduct(Cart cart) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(cart);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }
}
