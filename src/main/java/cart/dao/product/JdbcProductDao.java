package cart.dao.product;

import cart.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productEntityRowMapper = (resultSet, rowNum) -> Product.of(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    @Override
    public void insert(final Product product) {
        final String sql = "INSERT INTO product(name, image_url, price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getImageUrl(), product.getPrice());
    }

    @Override
    public Product findById(final Long id) {
        final String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productEntityRowMapper, id);
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void updateById(final Long id, final Product product) {
        final String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getImageUrl(), product.getPrice(), id);
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
