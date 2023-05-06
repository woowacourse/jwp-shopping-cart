package cart.repository;

import cart.service.dto.CartResponse;
import cart.service.dto.ProductResponse;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcShoppingCartRepository implements ShoppingCartRepository {

    private static final RowMapper<CartResponse> CART_RESPONSE_ROW_MAPPER = (rs, rowNumber) -> {
        final long cartId = rs.getLong("cart_id");
        final long productId = rs.getLong("product_id");
        final String name = rs.getString("name");
        final String imageUrl = rs.getString("image_url");
        final int price = rs.getInt("price");

        return new CartResponse(cartId, new ProductResponse(productId, name, imageUrl, price));
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcShoppingCartRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addProduct(final long memberId, final long productId) {
        final String sql = "insert into product_in_cart (member_id, product_id) values (:memberId, :productId)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("productId", productId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void removeProduct(final long cartId) {
        final String sql = "delete from product_in_cart where id=:cartId";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("cartId", cartId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<CartResponse> findAllProduct(final long memberId) {
        //쿼리가 이게 최선인지 고민해보기
        final String sql = "select product_in_cart.id as cart_id, product.id as product_id, name, image_url, price "
                + "from product_in_cart "
                + "inner join product "
                + "on product.id = product_in_cart.product_id "
                + "where product_in_cart.member_id = :member_id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, CART_RESPONSE_ROW_MAPPER);
    }
}
