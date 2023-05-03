package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ProductEntity> productEntityRowMapper = (resultSet, rowNum) -> ProductEntity.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image"),
            resultSet.getInt("price")
    );

    @Override
    @Transactional(readOnly = true)
    public List<ProductEntity> selectAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public long insert(final ProductEntity productEntity) {
        final String sql = "INSERT INTO product(name, image, price) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    @Override
    public int updateById(final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice(), productEntity.getId());
    }

    @Override
    public int deleteById(final ProductEntity productEntity) {
        final String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, productEntity.getId());
    }
}
