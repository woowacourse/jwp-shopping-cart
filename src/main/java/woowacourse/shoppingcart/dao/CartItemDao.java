package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dto.CartDto;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final JdbcTemplate jdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<Long> findIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), customerId);
    }

    public Long findProductIdById(final Long cartId) {
        try {
            final String sql = "SELECT product_id FROM cart_item WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rs.getLong("product_id"), cartId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidCartItemException();
        }
    }

    public void deleteCartItem(Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = ? and product_id = ?";
        jdbcTemplate.update(sql, customerId, productId);
    }

    public List<CartDto> getCartinfosByIds(List<Long> cartIds) {
        String value = cartIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        final String query = String.format("SELECT id, product_id, quantity FROM cart_item WHERE id IN (%s)", value);
        List<CartDto> cartDtos = jdbcTemplate.query(query,
                (rs, rowNum) -> new CartDto(rs.getLong("id"), rs.getLong("product_id"), rs.getInt("quantity")));

        return cartIds.stream()
                .flatMap(id -> cartDtos.stream()
                        .filter(cartDto -> cartDto.getProductId().equals(id)))
                .collect(Collectors.toList());
    }
}
