package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;

@Repository
public class CartItemDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<CartItemEntity> ROW_MAPPER = (rs, rownum) -> new CartItemEntity(
            rs.getLong("id"),
            rs.getLong("customer_id"),
            rs.getLong("product_id")
    );

    public CartItemDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(CartItemEntity cartItemEntity) {
        String sql = "INSERT INTO cart_item(customer_id, product_id) VALUES(:customerId, :productId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(cartItemEntity);

        jdbcTemplate.update(sql, source, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<CartItemEntity> findById(Long cartId) {
        String sql = "SELECT id, customer_id, product_id FROM cart_item WHERE id = :id";
        SqlParameterSource source = new MapSqlParameterSource("id", cartId);

        List<CartItemEntity> cartItemEntities = jdbcTemplate.query(sql, source, ROW_MAPPER);
        return Optional.ofNullable(DataAccessUtils.singleResult(cartItemEntities));
    }

    public List<CartItemEntity> findByCustomerId(Long customerId) {
        String sql = "SELECT id, customer_id, product_id FROM cart_item WHERE customer_id = :customerId";
        SqlParameterSource source = new MapSqlParameterSource("customerId", customerId);

        return jdbcTemplate.query(sql, source, ROW_MAPPER);
    }

    public void delete(CartItemEntity cartItemEntity) {
        String sql = "DELETE FROM cart_item WHERE customer_id = :customerId AND product_id = :productId";
        SqlParameterSource source = new BeanPropertySqlParameterSource(cartItemEntity);
        jdbcTemplate.update(sql, source);
    }
}
