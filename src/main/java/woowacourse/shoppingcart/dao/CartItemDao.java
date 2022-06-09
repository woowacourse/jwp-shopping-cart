package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.dto.CartItemDto;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@Repository
public class CartItemDao {

    private static final RowMapper<CartItemDto> ROW_MAPPER = (resultSet, rowNum) ->
            new CartItemDto(
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity"));

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public CartItemDao(final JdbcTemplate jdbcTemplate,
                       NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Long> findProductIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT product_id FROM cart_item WHERE customer_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("product_id"), customerId);
    }

    public List<CartItemDto> findCartItemByCustomerId(final Long customerId) {
        final String sql = "SELECT * FROM cart_item where customer_id = :customerId";
        SqlParameterSource params = new MapSqlParameterSource("customerId", customerId);

        return namedJdbcTemplate.query(sql, params, ROW_MAPPER);
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

    public Long addCartItem(final Long customerId, final Long productId) {
        final String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setLong(1, customerId);
            preparedStatement.setLong(2, productId);
            return preparedStatement;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteCartItem(final Long customerId, final Long productId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customerId and product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("productId", productId);

        final int rowCount = namedJdbcTemplate.update(sql, params);
        if (rowCount == 0) {
            throw new InvalidCartItemException();
        }
    }

    public boolean isExistItem(Long customerId, Long productId) {
        String sql = "select exists (select 1 from cart_item where customer_id = :customerId and product_id = :productId)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("productId", productId);
        return Objects.requireNonNull(namedJdbcTemplate.queryForObject(sql, params, Boolean.class));
    }

    public void updateQuantity(Long customerId, Long productId, int quantity) {
        String sql = "update cart_item set quantity = :quantity"
                + " where customer_id = :customerId"
                + " and product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("quantity", quantity);
        params.addValue("customerId", customerId);
        params.addValue("productId", productId);

        namedJdbcTemplate.update(sql, params);
    }

    public void deleteAllCartItem(Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE customer_id = :customerId";
        SqlParameterSource params = new MapSqlParameterSource("customerId", customerId);
        namedJdbcTemplate.update(sql, params);
    }
}
