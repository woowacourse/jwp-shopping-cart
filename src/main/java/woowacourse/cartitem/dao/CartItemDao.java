package woowacourse.cartitem.dao;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.cartitem.domain.CartItem;
import woowacourse.cartitem.domain.Quantity;
import woowacourse.product.domain.Price;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public CartItemDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("cart_item")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final Long customerId, final Long productId, final int quantity) {
        final SqlParameterSource params = new MapSqlParameterSource()
            .addValue("customer_id", customerId)
            .addValue("product_id", productId)
            .addValue("quantity", quantity);

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<CartItem> findAllByCustomerId(final Long customerId) {
        final String sql = "SELECT c.id, c.product_id, p.name, p.price, c.quantity, p.imageURL "
            + "FROM cart_item c "
            + "LEFT JOIN product p ON c.product_id = p.id "
            + "WHERE customer_id = :customer_id";
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId);

        return jdbcTemplate.query(sql, params, rowMapper());
    }

    public Optional<CartItem> findById(final Long id) {
        final String sql = "SELECT c.id, c.product_id, p.name, p.price, c.quantity, p.imageURL "
            + "FROM cart_item c "
            + "LEFT JOIN product p ON c.product_id = p.id "
            + "WHERE c.id = :id";
        final SqlParameterSource params = new MapSqlParameterSource("id", id);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, rowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Long> findAllIdsByCustomerId(final Long customerId) {
        final String sql = "SELECT id FROM cart_item WHERE customer_id = :customer_id";
        final SqlParameterSource params = new MapSqlParameterSource("customer_id", customerId);

        return jdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong("id"));
    }

    private RowMapper<CartItem> rowMapper() {
        return (rs, rowNum) -> new CartItem(
            rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getString("name"),
            new Price(rs.getInt("price")),
            rs.getString("imageURL"),
            new Quantity(rs.getInt("quantity"))
        );
    }

    public void update(final Long id, final CartItem cartItem) {
        final String sql = "UPDATE cart_item SET quantity = :quantity WHERE id = :id";
        final SqlParameterSource params = new MapSqlParameterSource("quantity", cartItem.getQuantity().getValue())
            .addValue("id", id);

        jdbcTemplate.update(sql, params);
    }

    public void deleteByIdAndCustomerId(final Long id, final Long customerId) {
        final String sql = "DELETE FROM cart_item WHERE id = :id AND customer_id = :customer_id";
        final SqlParameterSource params = new MapSqlParameterSource("id", id)
            .addValue("customer_id", customerId);

        jdbcTemplate.update(sql, params);
    }
}
