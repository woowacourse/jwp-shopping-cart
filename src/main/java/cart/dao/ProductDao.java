package cart.dao;

import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> selectAll() {
        final String sql = "SELECT * FROM PRODUCT";
        return jdbcTemplate.query(sql, getProductRowMapper());
    }

    private RowMapper<ProductEntity> getProductRowMapper() {
        return (resultSet, rowNum) -> new ProductEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image")
        );
    }

    public int insert(final Product newProduct) {
        final String sql = "INSERT INTO PRODUCT (name, price, image) VALUES (?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getImage()
        );
    }

    public void update(final Product product, final Long id) {
        final String sql = "UPDATE PRODUCT SET name = ?, price = ?, image = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                product.getName(),
                product.getPrice(),
                product.getImage(),
                id
        );
    }

    public void delete(final Long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
