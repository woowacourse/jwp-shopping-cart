package cart.persistence;

import cart.service.cart.CartDao;
import cart.service.cart.domain.CartItem;
import cart.service.cart.domain.CartItems;
import cart.service.product.domain.Product;
import cart.service.product.domain.ProductImage;
import cart.service.product.domain.ProductName;
import cart.service.product.domain.ProductPrice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2CartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<CartItem> cartItemRowMapper = (resultSet, rowNum) ->
            new CartItem(
                    new Product(
                            resultSet.getLong("product_id"),
                            new ProductName(resultSet.getString("name")),
                            new ProductImage(resultSet.getString("image_url")),
                            new ProductPrice(resultSet.getInt("price"))
                    )
            );

    private final RowMapper<Long> deleteCartItemRowMapper = (resultSet, rowNum) ->
            resultSet.getLong("id");

    @Override
    public void deleteCartItem(long cartId) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    @Override
    public Optional<Long> findOneCartItem(long memberId, long productId) {
        String sql = "SELECT * FROM cart WHERE product_id = ? AND member_id = ?";
        return jdbcTemplate.query(sql, deleteCartItemRowMapper, productId, memberId).stream()
                .findAny();
    }

    @Override
    public Long addCartItem(long productId, long memberId) {
        CartEntity cartEntity = new CartEntity(productId, memberId);
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(cartEntity);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public CartItems findCartItemsByMemberId(long memberId) {
        String sql = "SELECT * FROM cart c JOIN product p ON c.product_id = p.id WHERE c.member_id = ?";
        List<CartItem> cartItems = jdbcTemplate.query(sql, cartItemRowMapper, memberId);
        return new CartItems(cartItems);
    }
}
