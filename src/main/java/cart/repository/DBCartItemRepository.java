package cart.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.entity.CartItem;
import cart.entity.CartItemRepository;

@Repository
public class DBCartItemRepository implements CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(CartItem cartItem) {
        String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, cartItem.getMemberId());
            preparedStatement.setLong(2, cartItem.getProductId());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public CartItem findById(Long id) {
        String sql = "SELECT id, member_id, product_id FROM cart WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> CartItem.of(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
            ), id);
    }

    @Override
    public List<CartItem> findAll(Long memberId) {
        String sql = "SELECT id, member_id, product_id FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> CartItem.of(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
            ), memberId);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteByProductID(Long productId) {
        String sql = "DELETE FROM cart WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
