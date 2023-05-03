package cart.dao.product;

import cart.domain.product.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) -> ProductEntity.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    @Override
    public void insert(final ProductEntity productEntity) {
        final String sql = "INSERT INTO product(name, image_url, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductEntity> findAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void updateById(final Long id, final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice(), id);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}