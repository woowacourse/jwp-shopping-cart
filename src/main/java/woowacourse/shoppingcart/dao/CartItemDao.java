package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.OptionalLong;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.IdDto;
import woowacourse.shoppingcart.dao.dto.cartitem.AddQuantityDto;
import woowacourse.shoppingcart.dao.dto.cartitem.CartItemDto;
import woowacourse.shoppingcart.dao.dto.cartitem.CartItemInsertDto;
import woowacourse.shoppingcart.dao.dto.cartitem.UpdateQuantityDto;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public List<Long> findProductIdsByCustomerId(Long customerId) {
        String sql = "SELECT product_id FROM cart_item WHERE customer_id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(customerId));
        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("product_id"));
    }

    public List<Long> findIdsByCustomerId(Long customerId) {
        String sql = "SELECT id FROM cart_item WHERE customer_id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(customerId));
        return jdbcTemplate.query(sql, parameterSource, (rs, rowNum) -> rs.getLong("id"));
    }

    public List<CartItemDto> findCartItemsByCustomerId(Long customerId) {
        String sql = "SELECT id, customer_id, product_id, quantity FROM cart_item WHERE customer_id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(customerId));
        return jdbcTemplate.query(sql, parameterSource, mapToCartItem());
    }

    private RowMapper<CartItemDto> mapToCartItem() {
        return (resultSet, rowNum) -> new CartItemDto(
                resultSet.getLong("id"),
                resultSet.getLong("customer_id"),
                resultSet.getLong("product_id"),
                resultSet.getInt("quantity")
        );
    }

    public OptionalLong findProductIdById(Long cartItemId) {
        try {
            String sql = "SELECT product_id FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(cartItemId));
            return OptionalLong.of(Objects.requireNonNull(jdbcTemplate.queryForObject(sql, parameterSource,
                    (rs, rowNum) -> rs.getLong("product_id"))));
        } catch (EmptyResultDataAccessException e) {
            return OptionalLong.empty();
        }
    }

    public OptionalInt findQuantityById(Long cartItemId) {
        try {
            String sql = "SELECT quantity FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(cartItemId));
            return OptionalInt.of(
                    Objects.requireNonNull(jdbcTemplate.queryForObject(sql, parameterSource, Integer.class)));
        } catch (EmptyResultDataAccessException e) {
            return OptionalInt.empty();
        }
    }

    public Long addCartItem(Long customerId, Long productId, int quantity) {
        CartItemInsertDto cartItemInsertDto = new CartItemInsertDto(customerId, productId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartItemInsertDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public void deleteCartItem(Long cartItemId) {
        String sql = "DELETE FROM cart_item WHERE id = :id";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(cartItemId));
        int rowCount = jdbcTemplate.update(sql, parameterSource);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public void addQuantity(Long cartItemId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = quantity + :quantity WHERE id = :id";
        AddQuantityDto addQuantityDto = new AddQuantityDto(cartItemId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(addQuantityDto);
        jdbcTemplate.update(sql, parameterSource);
    }

    public void updateQuantity(Long cartItemId, int quantity, Long customerId) {
        String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id AND customer_id = :customerId";
        UpdateQuantityDto updateQuantityDto = new UpdateQuantityDto(cartItemId, quantity, customerId);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(updateQuantityDto);
        jdbcTemplate.update(sql, parameterSource);
    }

    public OptionalLong findIdByProductId(Long productId) {
        try {
            String sql = "SELECT id FROM cart_item WHERE product_id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(productId));
            return OptionalLong.of(
                    Objects.requireNonNull(jdbcTemplate.queryForObject(sql, parameterSource, Long.class)));
        } catch (EmptyResultDataAccessException e) {
            return OptionalLong.empty();
        }
    }
}
