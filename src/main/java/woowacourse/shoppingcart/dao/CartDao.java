package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.dto.SaveCartDto;
import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public class CartDao {
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    private static RowMapper<Cart> rowMapper() {
        return (rs, rowNum) ->
                new Cart(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getInt("quantity"));
    }

    public long save(SaveCartDto saveCartDto) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(saveCartDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<Cart> findCartByMemberId(long memberId) {
        String sql = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.member_id = ?";

        return jdbcTemplate.query(sql, rowMapper(), memberId);
    }

    public Optional<Cart> findCartByMemberIdAndProductId(long memberId, long productId) {
        String sql = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.member_id = ? AND p.id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), memberId, productId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Cart> findCartById(long cartId) {
        try {
            String sql = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                    "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.id = ?";
            Cart cart = jdbcTemplate.queryForObject(sql, rowMapper(), cartId);
            return Optional.ofNullable(cart);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateQuantity(long cartId, int quantity) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, quantity, cartId);
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
