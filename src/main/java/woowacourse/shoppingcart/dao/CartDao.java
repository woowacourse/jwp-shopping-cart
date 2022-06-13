package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CartDao {
    private static final int DEFAULT_ADD_ITEM_QUANTITY = 1;

    private static final RowMapper<CartItem> CART_ROW_MAPPER = (resultSet, rowNum) -> CartItem.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("thumbnail"),
            resultSet.getInt("quantity")
    );

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleCartItemJdbcInsert;

    public CartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleCartItemJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long addCartItem(final String customerUsername, final Long productId) {
        final SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("customer_username", customerUsername)
                        .addValue("product_id", productId)
                        .addValue("quantity", DEFAULT_ADD_ITEM_QUANTITY);

        return simpleCartItemJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<CartItem> findCartItemsByCustomerUsername(final String customerUsername) {
        final String sql = "SELECT product.id as id, " +
                "product.name as name, " +
                "product.price as price, " +
                "product.thumbnail as thumbnail, " +
                "cart_item.quantity as quantity " +
                "FROM cart_item " +
                "INNER JOIN product " +
                "ON cart_item.product_id = product.id " +
                "WHERE cart_item.customer_username = :customerUsername";

        return jdbcTemplate.query(sql, Map.of("customerUsername", customerUsername), CART_ROW_MAPPER);
    }

    public List<Long> findProductIdsByCustomerUsername(final String customerUsername) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_username = :customer_username";

        return jdbcTemplate.query(sql, Map.of("customer_username", customerUsername),
                (rs, rowNum) -> rs.getLong("product_id"));
    }

    public Optional<CartItem> findCartItemByProductId(final Long productId, final String customerUsername) {
        try {
            final String sql = "SELECT product.id as id, " +
                    "product.name as name, " +
                    "product.price as price, " +
                    "product.thumbnail as thumbnail, " +
                    "cart_item.quantity as quantity " +
                    "FROM cart_item " +
                    "INNER JOIN product " +
                    "ON cart_item.product_id = product.id " +
                    "WHERE cart_item.product_id = :product_id and cart_item.customer_username = :customer_username";

            final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("product_id", productId)
                    .addValue("customer_username", customerUsername);

            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, sqlParameterSource, CART_ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateCartItemQuantity(final int quantity, final Long productId, final String customerUsername) {
        final String sql = "UPDATE cart_item SET quantity = :quantity WHERE product_id = :product_id " +
                "and customer_username = :customer_username";
        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("quantity", quantity)
                .addValue("product_id", productId)
                .addValue("customer_username", customerUsername);

        final int rowCount = jdbcTemplate.update(sql, sqlParameterSource);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(final Long productId, final String customerUsername) {
        final String sql =
                "DELETE FROM cart_item WHERE product_id = :product_id and customer_username = :customer_username";

        final SqlParameterSource sqlParameterSource = new MapSqlParameterSource("product_id", productId)
                .addValue("customer_username", customerUsername);
        final int rowCount = jdbcTemplate.update(sql, sqlParameterSource);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteAllCartItems(final String customerUsername) {
        final String sql = "DELETE FROM cart_item WHERE customer_username = :customer_username";

        final int rowCount = jdbcTemplate.update(sql, Map.of("customer_username", customerUsername));
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }
}
