package cart.dao;

import cart.dao.entity.ProductEntity;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLCartDao implements CartDao {

    private final JdbcTemplate jdbcTemplate;

    public MySQLCartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long add(Long memberId, Long productId) {
        String query = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, memberId);
            ps.setLong(2, productId);
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<ProductEntity> findByMemberId(Long memberId) {
        String query = "SELECT * FROM cart "
            + "LEFT OUTER JOIN product ON cart.product_id = product.id "
            + "WHERE cart.member_id = ?";

        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")), memberId);

    }

    @Override
    public boolean isExistEntity(Long memberId, Long productId) {
        String query = "SELECT id FROM cart WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.query(query,
            (resultSet, rowNum) -> resultSet.getLong("id"), memberId, productId).size() == 1;
    }

    @Override
    public int deleteById(Long memberId, Long productId) {
        String query = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.update(query, memberId, productId);
    }

    @Override
    public int deleteByProductId(Long productId) {
        String query = "DELETE FROM cart WHERE product_id = ?";
        return jdbcTemplate.update(query, productId);
    }
}
