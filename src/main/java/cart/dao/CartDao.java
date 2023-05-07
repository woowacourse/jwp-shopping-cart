package cart.dao;

import cart.domain.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<CartData> cartDataRowMapper = (resultSet, rowNumber) -> new CartData(
            resultSet.getLong("id"),
            new Name(resultSet.getString("name")),
            new ImageUrl(resultSet.getString("image_url")),
            new Price(resultSet.getInt("price"))
    );

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

    public List<CartData> findAll(final Long userId) {
        final String sql = "SELECT carts.id, items.name, items.image_url, items.price " +
                "FROM items JOIN carts " +
                "ON items.id = carts.item_id " +
                "WHERE carts.user_id = :user_id";
        MapSqlParameterSource param = new MapSqlParameterSource("user_id", userId);
        return namedParameterJdbcTemplate.query(sql, param, cartDataRowMapper);
    }

    public void deleteBy(final Long cartId) {
        final String sql = "DELETE FROM carts WHERE id = :cart_id";
        MapSqlParameterSource param = new MapSqlParameterSource("cart_id", cartId);
        namedParameterJdbcTemplate.update(sql, param);
    }
}
