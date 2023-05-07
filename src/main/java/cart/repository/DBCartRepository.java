package cart.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import cart.entity.Cart;
import cart.entity.CartRepository;

@Repository
public class DBCartRepository implements CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public DBCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Cart cart) {
        String sql = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] {"id"});
            preparedStatement.setLong(1, cart.getMemberId());
            preparedStatement.setLong(2, cart.getProductId());
            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Cart findById(Long id) {
        String sql = "SELECT id, member_id, product_id FROM cart WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
            (resultSet, rowNum) -> Cart.of(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
            ), id);
    }

    @Override
    public List<Cart> findAll(Long memberId) {
        String sql = "SELECT id, member_id, product_id FROM cart WHERE member_id = ?";
        return jdbcTemplate.query(sql,
            (resultSet, rowNum) -> Cart.of(
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
