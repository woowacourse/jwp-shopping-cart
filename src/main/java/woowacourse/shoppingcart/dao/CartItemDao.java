package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.dto.EnrollCartDto;
import woowacourse.shoppingcart.domain.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_item")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(EnrollCartDto enrollCartDto) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(enrollCartDto);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<Cart> findCartByMemberId(Long memberId) {
        String SQL = "SELECT c.id, c.product_id, p.name, p.price, p.image_url, c.quantity " +
                "FROM cart_item as c JOIN product as p ON c.product_id = p.id WHERE c.member_id = ?";

        return jdbcTemplate.query(SQL, (rs, rowNum) ->
                new Cart(
                        rs.getLong("id"),
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url"),
                        rs.getInt("quantity")), memberId);
    }

    public Optional<Long> findProductIdById(Long cartId) {
        try {
            String SQL = "SELECT product_id FROM cart_item WHERE id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL,
                    (rs, rowNum) -> rs.getLong("product_id"), cartId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
