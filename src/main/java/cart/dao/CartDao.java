package cart.dao;

import cart.dao.entity.ProductEntity;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {

    private final JdbcTemplate jdbcTemplate;

    public CartDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long add(Long memberId, Long productId) {
        String query = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, memberId);
            ps.setLong(2, productId);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<ProductEntity> findAllByMemberId(Long memberId) {
        String query = "SELECT p.id, p.name, p.price, p.image_url "
            + "FROM cart as c "
            + "JOIN product as p "
            + "ON c.product_id = p.id "
            + "AND c.member_id = ?";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")), memberId);
    }

    public Optional<Long> findCartIdByMemberIdAndProductId(Long memberId, Long productId) {
        String query = "SELECT id FROM cart WHERE member_id = ? AND product_id = ?";
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(query, Long.class, memberId, productId));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }


    public int deleteByMemberIdAndProductId(Long memberId, Long productId) {
        String query = "DELETE FROM cart WHERE member_id = ? AND product_id = ?";
        return jdbcTemplate.update(query, memberId, productId);
    }
}
