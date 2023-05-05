package cart.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import cart.dao.dto.CartItemDto;

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

    public void insert(Integer userId, Integer productId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("product_id", productId);

        simpleJdbcInsert.execute(parameterSource);
    }

    public void delete(Integer userId, Integer productId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ? AND product_id = ?";

        jdbcTemplate.update(sql, userId, productId);
    }

    public void deleteAll(Integer userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?";

        jdbcTemplate.update(sql, userId);
    }

    public List<CartItemDto> selectAllItemsOf(Integer userId) {
        String sql = "SELECT id, user_id, product_id FROM cart_item WHERE user_id = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new CartItemDto(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id")
                ),
                userId
        );
    }

    public CartItemDto selectBy(Integer id) {
        String sql = "SELECT id, user_id, product_id FROM cart_item WHERE id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new CartItemDto(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id")
                ),
                id
        );
    }
}
