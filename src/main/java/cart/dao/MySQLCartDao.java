package cart.dao;

import cart.controller.dto.CartRequest;
import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public long add(CartRequest request) {
        String query = "INSERT INTO cart (member_id, product_id) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setLong(1, request.getMemberId());
            ps.setLong(2, request.getProductId());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<ProductEntity> findByMeberId(Long memberId) {
        String query = "SELECT * FROM cart LEFT OUTER JOIN product ON cart.product_id = product.id where cart.member_id = ?;";
        return jdbcTemplate.query(query, (resultSet, rowNum) ->
            new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")), memberId);

    }

    @Override
    public Optional<CartEntity> findById(Long id) {
        String query = "SELECT * FROM cart WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNum) ->
                new CartEntity(
                    resultSet.getLong("id"),
                    resultSet.getLong("member_id"),
                    resultSet.getLong("product_id")), id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(Long id) {
        String query = "DELETE FROM cart WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }
}
