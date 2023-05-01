package cart.repository.cart;

import cart.domain.cart.Cart;
import cart.domain.cart.CartId;
import cart.domain.member.MemberId;
import cart.domain.product.Product;
import cart.domain.product.ProductId;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cart.repository.cart.CartJdbcRepository.Table.*;

@Repository
public class CartJdbcRepository implements CartRepository {
    enum Table {
        TABLE("carts"),
        ID("id"),
        MEMBER_ID("member_id"),
        PRODUCT_ID("product_id");

        private final String name;

        Table(final String name) {
            this.name = name;
        }
    }
    
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE.name)
                .usingGeneratedKeyColumns(ID.name);
    }

    @Override
    public CartId save(final Cart cart) {
        final long cartId = simpleJdbcInsert.executeAndReturnKey(paramSource(cart)).longValue();
        return CartId.from(cartId);
    }

    private SqlParameterSource paramSource(final Cart cart) {
        return new MapSqlParameterSource()
                .addValue(MEMBER_ID.name, cart.getMemberId().getId());
    }

    @Override
    public Optional<Cart> joinProductsByMemberId(final MemberId memberId) {
        final String sql = new StringBuilder()
                .append("SELECT")
                .append(" c.id,")
                .append(" c.member_id AS member_id,")
                .append(" p.id AS product_id,")
                .append(" p.name AS product_name,")
                .append(" p.price AS product_price,")
                .append(" p.image AS product_image")
                .append(" FROM carts c, cart_product cp, products p")
                .append(" WHERE c.member_id = ?")
                .append(" AND c.id = cp.cart_id")
                .append(" AND cp.product_id = p.id")
                .toString();

        try {
            final Cart cart = jdbcTemplate.queryForObject(sql, rowMapperJoinProducts, memberId.getId());
            return Optional.ofNullable(cart);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private final RowMapper<Cart> rowMapperJoinProducts = (rs, rowNum) -> {
        final CartId cartId = CartId.from(rs.getLong(ID.name));
        final MemberId memberId = MemberId.from(rs.getLong(MEMBER_ID.name));

        final List<Product> products = new ArrayList<>();
        do {
            final Product product = new Product(
                    ProductId.from(rs.getLong(PRODUCT_ID.name)),
                    rs.getString("product_name"),
                    rs.getDouble("product_price"),
                    rs.getString("product_image"));

            products.add(product);
        } while (rs.next());

        return new Cart(cartId, memberId, products);
    };

    @Override
    public CartId deleteByMemberId(final MemberId memberId) {
        return CartId.from(0);
    }
}
