package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.dto.CartItem;

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

    public Long save(Long memberId, Long productId) {
        CartItem cartItem = new CartItem(memberId, productId);
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(cartItem);
        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public List<Long> findProductIdsByMemberId(Long memberId) {
        String SQL = "SELECT product_id FROM cart_item WHERE member_id = ?";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> rs.getLong("product_id"), memberId);
    }

    public List<Long> findIdsByMemberId(Long memberId) {
        String SQL = "SELECT id FROM cart_item WHERE member_id = ?";

        return jdbcTemplate.query(SQL, (rs, rowNum) -> rs.getLong("id"), memberId);
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

    public void deleteById(Long id) {
        String SQL = "DELETE FROM cart_item WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }
}
