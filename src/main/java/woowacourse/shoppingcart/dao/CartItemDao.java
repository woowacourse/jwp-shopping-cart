package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dto.CartDto;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CartDto> rowMapper = (rs, rowNum) -> new CartDto(rs.getLong("id"), rs.getLong("product_id"),
            rs.getInt("quantity"));

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id, quantity) VALUES(?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            preparedStatement.setInt(3, 1);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public boolean existProduct(Long customerId, Long productId) {
        final String query = "SELECT EXISTS(SELECT * FROM cart_item WHERE customer_id =? and product_id = ?)";
        return jdbcTemplate.queryForObject(query, Boolean.class, customerId, productId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public List<CartDto> getCartinfosByIds(List<Long> cartIds) {
        String value = cartIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        final String query = String.format("SELECT id, product_id, quantity FROM cart_item WHERE id IN (%s)", value);
        List<CartDto> cartDtos = jdbcTemplate.query(query, rowMapper);

        return cartIds.stream()
                .flatMap(id -> cartDtos.stream()
                        .filter(cartDto -> cartDto.getProductId().equals(id)))
                .collect(Collectors.toList());
    }

    public void updateCartItem(Long customerId, int quantity, Long productId) {
        final String query = "UPDATE cart_item SET quantity =? WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(query, quantity, customerId, productId);
    }

    public CartDto findCartByProductCustomer(Long customerId, Long productId) {
        final String query = "SELECT id, quantity, product_id FROM cart_item WHERE customer_id = ? and product_id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, customerId, productId);
    }

    public void deleteCartItem(Long customerId, final Long productId) {
        final String query = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(query, customerId, productId);
    }
}
