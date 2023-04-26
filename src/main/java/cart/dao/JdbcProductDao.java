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

    public List<ProductEntity> selectAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    public void insert(final ProductEntity productEntity){
        String sql = "INSERT INTO product(name, image, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice());
    }

    public void update(final ProductEntity productEntity){
        String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImage(), productEntity.getPrice(), productEntity.getId());
    }

    public void delete(final ProductEntity productEntity){
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, productEntity.getId());
    }
}
