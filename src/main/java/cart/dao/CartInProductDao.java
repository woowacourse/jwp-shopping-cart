package cart.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

@Component
public class CartInProductDao {

    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartInProductDao(final JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CART_IN_PRODUCT")
                .usingGeneratedKeyColumns("id");
    }

    public void save(final CartInProductEntity cartInProductEntity) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(cartInProductEntity));
    }
}
