package cart.repository.cartproduct;

import cart.domain.cart.CartId;
import cart.domain.cartproduct.CartProduct;
import cart.domain.cartproduct.CartProductId;
import cart.domain.product.ProductId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static cart.repository.cartproduct.CartProductJdbcRepository.Table.*;

@Repository
public class CartProductJdbcRepository implements CartProductRepository {
    enum Table {
        TABLE("cart_product"),
        ID("id"),
        CART_ID("cart_id"),
        PRODUCT_ID("product_id");

        private final String name;

        Table(final String name) {
            this.name = name;
        }
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartProductJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE.name)
                .usingGeneratedKeyColumns(ID.name);
    }

    @Override
    public CartProductId save(final CartProduct cartProduct) {
        final long cartProductId = simpleJdbcInsert.executeAndReturnKey(paramSource(cartProduct)).longValue();
        return CartProductId.from(cartProductId);
    }

    private SqlParameterSource paramSource(final CartProduct cartProduct) {
        return new MapSqlParameterSource()
                .addValue(CART_ID.name, cartProduct.getCartId().getId())
                .addValue(PRODUCT_ID.name, cartProduct.getProductId().getId());
    }

    @Override
    public Optional<CartProduct> findByCartProductId(final CartProductId cartProductId) {
        final String sql = "SELECT * FROM cart_product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, cartProductId.getId()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<CartProduct> rowMapper = (rs, rowNum) -> {
        return new CartProduct(
                CartProductId.from(rs.getLong(ID.name)),
                CartId.from(rs.getLong(CART_ID.name)),
                ProductId.from(rs.getLong(PRODUCT_ID.name))
        );
    };
}
