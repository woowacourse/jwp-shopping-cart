package cart.dao;

import cart.domain.entity.Product;
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

    private final RowMapper<Product> productEntityRowMapper = (resultSet, rowNum) -> Product.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image"),
            resultSet.getInt("price")
    );

    @Override
    @Transactional(readOnly = true)
    public List<Product> selectAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public Product selectById(final long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
    }

    @Override
    public long insert(final Product product) {
        final String sql = "INSERT INTO product(name, image, price) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getImage(), product.getPrice());
    }

    @Override
    public int update(final Product product) {
        final String sql = "UPDATE product SET name = ?, image = ?, price = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getImage(), product.getPrice(), product.getId());
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
