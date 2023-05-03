package cart.dao;

import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDao {

    private static final RowMapper<ProductEntity> rowMapper =
            (rs, rowNum) -> new ProductEntity(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4)
            );

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(final ProductEntity product) {
        String sql = "INSERT INTO PRODUCT (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Optional<ProductEntity> findById(final Long id) {
        String sql = "SELECT * FROM PRODUCT WHERE ID = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, (rs, rowNum)
                -> new ProductEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getInt("price"))
        );
    }

    public void updateById(final Long id, final ProductEntity product) {
        String sql = "UPDATE PRODUCT SET(name, price, image_url) = (?, ?, ?) WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
