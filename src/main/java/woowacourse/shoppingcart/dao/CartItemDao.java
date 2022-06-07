package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.common.exception.NotFoundException;
import woowacourse.common.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private static final RowMapper<CartItem> CART_ROW_MAPPER = (resultSet, rowNumber) ->
            new CartItem(
                    resultSet.getLong("id"),
                    resultSet.getInt("quantity"),
                    new Product(
                            resultSet.getLong("productId"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getInt("stock_quantity"),
                            new ThumbnailImage(
                                    resultSet.getString("url"),
                                    resultSet.getString("alt")
                            )
                    )
            );

    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CartItem getById(final Long cartId) {
        final String sql =
                "SELECT c.id, c.quantity, p.id AS productId, p.name, p.price, p.stock_quantity, t.url, t.alt "
                        + "FROM cart_item AS c, product AS p, thumbnail_image as t "
                        + "WHERE c.id = ? AND (c.product_id = p.id AND t.product_id = p.id)";
        try {
            return jdbcTemplate.queryForObject(sql, CART_ROW_MAPPER, cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("존재하지 않는 장바구니입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
    }

    public List<CartItem> getAllByCustomerId(final Long customerId) {
        final String sql =
                "SELECT c.id, c.quantity, p.id AS productId, p.name, p.price, p.stock_quantity, t.url, t.alt "
                        + "FROM cart_item AS c, product AS p, thumbnail_image as t "
                        + "WHERE c.customer_id = ? AND (c.product_id = p.id AND t.product_id = p.id)";

        return jdbcTemplate.query(sql, CART_ROW_MAPPER, customerId);
    }

    public Long save(final Long customerId, final int quantity, final Long productId) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("customer_id", customerId);
        params.put("quantity", quantity);
        params.put("product_id", productId);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void updateCartQuantity(CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";

        jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getId());
    }

    public boolean existsProductIdAndCustomerId(Long productId, Long customerId) {
        final String sql = "SELECT exists(SELECT id FROM cart_item WHERE customer_id = ? AND product_id = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, customerId, productId));
    }
}
