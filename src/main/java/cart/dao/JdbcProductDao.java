package cart.dao;

import cart.entity.ProductEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

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
    public List<ProductEntity> selectAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void insert(final ProductEntity productEntity) {
        String sql = "INSERT INTO product(name, image, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    @Override
    public void updateById(final Long id, final ProductEntity productEntity) {
        String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice(), id);
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
