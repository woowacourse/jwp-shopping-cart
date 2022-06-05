package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.CartItemEntity;

@Repository
public class JdbcCartItemDao implements CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long addCartItem(final int customerId, final Long productId, int quantity) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<CartItemEntity> findCartByCustomerId(int customerId) {
        final String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new CartItemEntity(
                                rs.getLong("id"),
                                rs.getInt("customer_id"),
                                rs.getLong("product_id"),
                                rs.getInt("quantity")
                        )
                , customerId);

    }

//    @Override
//    public Long findProductIdById(final Long cartId) {
//        final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
//        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
//
//    }`

//    public void deleteCartItem(final Long id) {
//        final String sql = "DELETE FROM cart_item WHERE id = ?";
//
//        final int rowCount = jdbcTemplate.update(sql, id);
//        if (rowCount == 0) {
//            throw new InvalidCartItemException();
//        }
//    }
}
