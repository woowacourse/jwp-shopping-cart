package cart.dao;

import cart.domain.cart.CartDao;
import cart.domain.product.Product;
import cart.domain.product.ProductCategory;
import cart.domain.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CartDaoImpl implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<Product> productRowMapper = (result, count) ->
            new Product(result.getLong("id"),
                    result.getString("name"),
                    result.getString("image_url"),
                    result.getInt("price"),
                    ProductCategory.from(result.getString("category"))
            );

    @Override
    public Long insert(final User user, final Long productId) {
        final String query = "INSERT INTO cart(product_id, user_id) VALUES(?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setInt(1, productId.intValue());
            ps.setInt(2, user.getId().intValue());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Product> findAllByUser(final User user) {
        final String query1 = "SELECT c.product_id FROM cart c WHERE c.user_id = ?";
        final List<Long> productIds = jdbcTemplate.query(query1,
                (result, count) -> result.getLong("product_id"), user.getId());

        final String query2 = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p WHERE p.id = ?";
        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            final Product product = jdbcTemplate.queryForObject(query2, productRowMapper, productId);
            products.add(product);
        }
        return products;
    }
}
