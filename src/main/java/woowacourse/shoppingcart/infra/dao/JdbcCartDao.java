package woowacourse.shoppingcart.infra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Component
public class JdbcCartDao implements CartDao {
    private static final String CART_ITEM_PRODUCT_JOIN_QUERY =
            "SELECT c.id AS cart_id, "
                    + "c.customer_id AS customer_id, "
                    + "p.id AS product_id, "
                    + "p.name AS product_name, "
                    + "p.price AS product_price, "
                    + "p.image_url AS product_image_url, "
                    + "c.quantity AS cart_quantity "
                    + "FROM cart_item c "
                    + "JOIN product p ON c.product_id = p.id "
                    + "WHERE c.customer_id = ?";

    private static final RowMapper<CartEntity> CART_ENTITY_ROW_MAPPER =
            (rs, rowNum) -> new CartEntity(
                    rs.getLong("cart_id"),
                    rs.getLong("customer_id"),
                    getProductEntity(rs),
                    rs.getInt("cart_quantity")
            );

    private static ProductEntity getProductEntity(final ResultSet rs) throws SQLException {
        return new ProductEntity(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getLong("product_price"),
                rs.getString("product_image_url")
        );
    }

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcCartDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<CartEntity> findCartsByMemberId(final long memberId) {
        final String sql = CART_ITEM_PRODUCT_JOIN_QUERY;

        return jdbcTemplate.query(sql, CART_ENTITY_ROW_MAPPER, memberId);
    }

    @Override
    public void save(final List<CartEntity> cartEntities) {
        deleteByCartIds(getCartIds(cartEntities));

        final String sql = "INSERT INTO Cart_Item(customer_id, product_id, quantity) VALUES(?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, getParams(cartEntities));
    }

    @Override
    public void deleteByCartIds(List<Long> cartIds) {
        if (cartIds.isEmpty()) {
            return;
        }

        final String inSql = String.join(",", Collections.nCopies(cartIds.size(), "?"));
        final String sql = "DELETE FROM cart_item WHERE id IN (%s)";

        jdbcTemplate.update(String.format(sql, inSql), cartIds.toArray());
    }

    private List<Long> getCartIds(final List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .filter(entity -> Objects.nonNull(entity.getId()))
                .map(CartEntity::getId)
                .collect(Collectors.toList());
    }

    private List<Object[]> getParams(final List<CartEntity> cartEntities) {
        return cartEntities.stream()
                .map(entity -> new Object[]{entity.getCustomerId(), entity.getProductEntity().getId(),
                        entity.getQuantity()})
                .collect(Collectors.toList());
    }
}
