package woowacourse.shoppingcart.cartitem.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.cartitem.domain.CartItem;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.domain.ThumbnailImage;
import woowacourse.shoppingcart.exception.NotExistException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CartItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long addCartItem(final Long customerId, final Long productId, final Integer quantity) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", customerId)
                .addValue("product_id", productId)
                .addValue("quantity", quantity);

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public void deleteCartItem(final Long id) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";

        final int rowCount = jdbcTemplate.update(sql, id);
        if (rowCount == 0) {
            throw new NotExistException("존재하지 않는 장바구니 아이템입니다.", ErrorResponse.NOT_EXIST_CART_ITEM);
        }
    }

    public List<CartItem> findAllByCustomerId(Long customerId) {
        final String sql =
                "select c.id as cid, c.quantity as quantity, p.id as pid, p.name as pname, p.price as price, p.stock_quantity as stock_quantity, i.url as url, i.alt as alt "
                        + "from cart_item as c "
                        + "inner join product as p on c.product_id = p.id "
                        + "inner join images as i on p.id = i.product_id "
                        + "where c.customer_id = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new CartItem(
                        rs.getLong("cid"),
                        new Product(rs.getLong("pid"), rs.getString("pname"),
                                rs.getInt("price"), rs.getLong("stock_quantity"),
                                new ThumbnailImage(rs.getString("url"), rs.getString("alt"))
                        ),
                        rs.getInt("quantity")
                ), customerId);
    }

    public CartItem findByCustomerId(Long customerId, Long cartItemId) {
        final String sql =
                "select c.id as cid, c.quantity as quantity, p.id as pid, p.name as pname, p.price as price, p.stock_quantity as stock_quantity, i.url as url, i.alt as alt "
                        + "from cart_item as c "
                        + "inner join product as p on c.product_id = p.id "
                        + "inner join images as i on p.id = i.product_id "
                        + "where c.id = ? and c.customer_id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new CartItem(
                        rs.getLong("cid"),
                        new Product(rs.getLong("pid"), rs.getString("pname"),
                                rs.getInt("price"), rs.getLong("stock_quantity"),
                                new ThumbnailImage(rs.getString("url"), rs.getString("alt"))
                        ),
                        rs.getInt("quantity")
                ), cartItemId, customerId);
    }

    public void updateQuantityByCustomerId(Long customerId, Long cartItemId, Integer quantity) {
        final String sql = "update cart_item set quantity = ? where customer_id = ? and id = ?";
        jdbcTemplate.update(sql, quantity, customerId, cartItemId);
    }

    public void deleteCartItemByIds(List<Long> ids) {
        final String sql = "delete from cart_item where id in (:ids)";
        final SqlParameterSource params = new MapSqlParameterSource("ids", ids);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<CartItem> findByIds(List<Long> ids) {
        final String sql =
                "select c.id as cid, c.quantity as quantity, p.id as pid, p.name as pname, p.price as price, p.stock_quantity as stock_quantity, i.url as url, i.alt as alt "
                        + "from cart_item as c "
                        + "inner join product as p on c.product_id = p.id "
                        + "inner join images as i on p.id = i.product_id "
                        + "where c.id in (:ids)";

        final SqlParameterSource params = new MapSqlParameterSource("ids", ids);

        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) ->
                new CartItem(
                        rs.getLong("cid"),
                        new Product(rs.getLong("pid"), rs.getString("pname"),
                                rs.getInt("price"), rs.getLong("stock_quantity"),
                                new ThumbnailImage(rs.getString("url"), rs.getString("alt"))
                        ),
                        rs.getInt("quantity")));
    }

    public boolean notExistByIdAndCustomerId(Long id, Long customerId) {
        final String sql = "select exists(select * from cart_item where id = ? and customer_id = ?)";
        return !jdbcTemplate.queryForObject(sql,Boolean.class, id, customerId);
    }

    public boolean existsByProductIdAndCustomerId(Long productId, Long customerId) {
        final String sql = "select exists(select * from cart_item where product_id = ? and customer_id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, productId, customerId);
    }
}
