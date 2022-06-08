package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.IdDto;
import woowacourse.shoppingcart.dao.dto.cartitem.AddQuantityDto;
import woowacourse.shoppingcart.dao.dto.cartitem.CartItemDto;
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

    public Optional<Long> findProductIdById(Long cartItemId) {
        try {
            String sql = "SELECT product_id FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(cartItemId));
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource,
                    (rs, rowNum) -> rs.getLong("product_id")));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Integer> findQuantityById(Long cartItemId) {
        try {
            String sql = "SELECT quantity FROM cart_item WHERE id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(cartItemId));
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, Integer.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Long addCartItem(Long customerId, Long productId, int quantity) {
        CartItemDto cartItemDto = new CartItemDto(customerId, productId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartItemDto);
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

    public void updateQuantity(Long cartItemId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id";
        UpdateQuantityDto updateQuantityDto = new UpdateQuantityDto(cartItemId, quantity);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(updateQuantityDto);
        jdbcTemplate.update(sql, parameterSource);
    }

    public Optional<Long> findIdByProductId(Long productId) {
        try {
            String sql = "SELECT id FROM cart_item WHERE product_id = :id";
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(new IdDto(productId));
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, parameterSource, Long.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
