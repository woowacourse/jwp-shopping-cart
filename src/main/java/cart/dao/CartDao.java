package cart.dao;

import cart.domain.Cart;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(final Cart cart) {
        final String sql = "INSERT INTO carts(user_id, item_id) VALUES(:userId, :itemId)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(cart);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder);
        return (Long) keyHolder.getKey();
    }
}
