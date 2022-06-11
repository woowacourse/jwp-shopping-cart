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

    public Long save(SaveCartDto saveCartDto) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(saveCartDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<Cart> findCartByMemberId(Long memberId) {
        String SQL = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.member_id = ?";

        return jdbcTemplate.query(SQL, cartMapper(), memberId);
    }

    public Optional<Cart> findCartById(Long cartId) {
        try {
            String SQL = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                    "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.id = ?";
            Cart cart = jdbcTemplate.queryForObject(SQL, cartMapper(), cartId);
            return Optional.ofNullable(cart);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Cart> cartMapper() {
        return (rs, rowNum) ->
                new Cart(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getInt("quantity"));
    }

    public void updateQuantity(Long cartId, int quantity) {
        String SQL = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(SQL, quantity, cartId);
    }

    public void deleteById(Long id) {
        String SQL = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
