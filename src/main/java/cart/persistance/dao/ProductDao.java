package cart.persistance.dao;

import cart.persistance.dao.exception.ProductNotFoundException;
import cart.persistance.entity.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public long add(final Product product) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        long id = simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();

        return id;
    }

    public List<Product> findAll() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        List<Product> products = jdbcTemplate.query(sql, (resultSet, rowNumber) -> Product.create(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getLong("price"),
                resultSet.getString("image_url")
        ));

        return products;
    }

    public void update(final Product productToUpdate) {
        final String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        final int affectedRow = jdbcTemplate.update(
                sql,
                productToUpdate.getName(),
                productToUpdate.getPrice(),
                productToUpdate.getImageUrl(),
                productToUpdate.getId()
        );

        throwProductNotFoundException(affectedRow);
    }

    public void deleteById(final long id) {
        final String sql = "DELETE FROM product WHERE id = ?";
        final int affectedRow = jdbcTemplate.update(sql, id);
        throwProductNotFoundException(affectedRow);
    }

    private void throwProductNotFoundException(final int affectedRow) {
        if (affectedRow == 0) {
            throw new ProductNotFoundException();
        }
    }
}
