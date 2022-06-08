package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class CartDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(final DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cart")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Cart cart) {
        final SqlParameterSource parameters = new MapSqlParameterSource("member_id", cart.getMemberId())
                .addValue("product_id", cart.getProduct().getId())
                .addValue("quantity", cart.getQuantity());
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    public List<Cart> findCartsByMemberId(final Long id) {
        String sql =
                "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM cart c "
                        + "LEFT JOIN product p "
                        + "ON c.product_id = p.id "
                        + "WHERE member_id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.query(sql, parameter, joinRowMapper());
    }

    public Cart findCartById(final Long id) {
        String sql = "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM cart c "
                + "LEFT JOIN product p "
                + "ON c.product_id = p.id "
                + "WHERE c.id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);

        return namedParameterJdbcTemplate.queryForObject(sql, parameter, joinRowMapper());
    }

    public void delete(final Long id) {
        String sql = "DELETE FROM cart WHERE id = :id";
        final SqlParameterSource parameter = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, parameter);
    }

    public List<Cart> findCartsByIds(final List<Long> cartIds) {
        String sql = "SELECT c.id, c.member_id, p.id as product_id, p.name, p.price, p.image_url, c.quantity FROM cart c "
                + "LEFT JOIN product p "
                + "ON c.product_id = p.id "
                + "WHERE c.id IN(:ids)";
        final SqlParameterSource parameter = new MapSqlParameterSource("ids", cartIds);

        return namedParameterJdbcTemplate.query(sql, parameter, joinRowMapper());
    }

    public void updateQuantity(final Cart cart) {
        String sql = "UPDATE cart SET quantity = :quantity WHERE id = :id";
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(cart);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void deleteBatch(final List<Long> cartIds) {
        String sql = "DELETE FROM cart WHERE id IN (:ids)";
        final SqlParameterSource[] parameters = cartIds.stream()
                .map(cartId -> new MapSqlParameterSource("ids", cartId))
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, parameters);
    }

    private RowMapper<Cart> joinRowMapper() {
        return (rs, rowNum) -> {
            final Long id = rs.getLong("id");
            final Long memberId = rs.getLong("member_id");
            final Long productId = rs.getLong("product_id");
            final String name = rs.getString("name");
            final int price = rs.getInt("price");
            final String imageUrl = rs.getString("image_url");
            final int quantity = rs.getInt("quantity");
            return new Cart(id, memberId, new Product(productId, name, price, imageUrl), quantity);
        };
    }
}
