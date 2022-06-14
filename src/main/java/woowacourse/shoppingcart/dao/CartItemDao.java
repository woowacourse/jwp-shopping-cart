package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.cart.InvalidCartItemException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Cart> cartRowMapper = (resultSet, rowNum) ->
            new Cart(
                    resultSet.getLong("cart_item.id"),
                    resultSet.getLong("product.id"),
                    resultSet.getString("product.name"),
                    resultSet.getInt("product.price"),
                    resultSet.getString("product.image_url"),
                    resultSet.getInt("cart_item.quantity")
            );

    public List<Cart> findCartByMemberId(final long memberId) {
        final String sql = "SELECT cart_item.id, product.id, cart_item.quantity, product.name, product.price , product.image_url " +
                "FROM cart_item INNER JOIN product ON cart_item.product_id = product.id " +
                "WHERE cart_item.member_id = ?";

        return jdbcTemplate.query(sql, cartRowMapper, memberId);
    }

    public long findIdIfExistByMemberProductId(final long memberId, final long productId) {
        try {
            final String sql = "SELECT id FROM cart_item WHERE member_id = ? AND product_id = ?";
            return jdbcTemplate.queryForObject(sql, Integer.class, memberId, productId);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public void plusQuantityById(long cartId) {
        final String sql = "UPDATE cart_item SET quantity = ((SELECT quantity FROM cart_item WHERE id = ?) + 1 ) WHERE id = ?";
        jdbcTemplate.update(sql, cartId, cartId);
    }

    public void add(final long memberId, final long productId, final int quantity) {
        simpleJdbcInsert.execute(Map.of(
                "member_id", memberId,
                "product_id", productId,
                "quantity", quantity
        ));
    }

    public void delete(final long cartId) {
        final String sql = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(sql, cartId);
    }


    public void update(long cartId, int quantity) {
        final String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, cartId);
    }

    public boolean isValidCartIdByMemberId(final long memberId, final long cartId) {
        final String sql = "SELECT EXISTS (SELECT * FROM cart_item WHERE member_id = ? AND id = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, memberId, cartId);
    }

    public long getProductIdById(final long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException("유효하지 않은 장바구니입니다.");
        }
    }
}
