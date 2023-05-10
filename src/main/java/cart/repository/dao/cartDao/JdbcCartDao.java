package cart.repository.dao.cartDao;

import cart.dto.CartItemDto;
import cart.entity.Cart;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcCartDao implements CartDao {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCartDao(final DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Long save(final Cart cart) {
        final SqlParameterSource source = new BeanPropertySqlParameterSource(cart);
        return (Long) simpleJdbcInsert.executeAndReturnKey(source);
    }

    @Override
    public List<CartItemDto> findAllCartItemsByMemberId(final Long memberId) {
        final String sql = "select CART.id, CART.product_id, PRODUCT.name, PRODUCT.image_url, PRODUCT.price " +
                "from CART INNER JOIN PRODUCT ON CART.product_id = PRODUCT.id " +
                "WHERE CART.member_id = :memberId";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        return template.query(sql, source, rowMapper());
    }

    @Override
    public int deleteByCartId(final Long cartId) {
        final String sql = "delete from cart where id = :cartId";
        final SqlParameterSource source = new MapSqlParameterSource()
                .addValue("cartId", cartId);
        return template.update(sql, source);
    }

    public RowMapper<CartItemDto> rowMapper() {
        return ((rs, rowNum) -> {
            long cartId = rs.getLong("id");
            long productId = rs.getLong("product_id");
            String productName = rs.getString("name");
            String imageUrl = rs.getString("image_url");
            int price = rs.getInt("price");
            return new CartItemDto(cartId, productId, productName, imageUrl, price);
        });
    }
}
