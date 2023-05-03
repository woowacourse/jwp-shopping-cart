package cart.repository;

import cart.entity.Product;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcShoppingCartRepository implements ShoppingCartRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcShoppingCartRepository(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addProduct(final long memberId, final long productId) {
        final String sql = "insert into cart (member_id, product_id) values (:memberId, :productId)";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("productId", productId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public void removeProduct(final long cartId) {
        final String sql = "delete from cart where id=:cartId";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("cartId", cartId);
        namedParameterJdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public List<Product> findAllProduct(final long memberId) {
        //조금 더 고민해보자
        final String sql = "select product.id, name, image_url, price "
                + "from cart "
                + "inner join product "
                + "on product.id = cart.product_id "
                + "where cart.member_id = :member_id";
        final SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("member_id", memberId);
        return namedParameterJdbcTemplate.query(sql, parameterSource, JdbcProductRepository.PRODUCT_ROW_MAPPER);
    }
}
