package cart.persistence;

import cart.service.product.ProductDao;
import cart.service.product.domain.Product;
import cart.service.product.domain.ProductImage;
import cart.service.product.domain.ProductName;
import cart.service.product.domain.ProductPrice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2ProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> productEntityRowMapper = (resultSet, rowNum) ->
            new Product(
                    resultSet.getLong("id"),
                    new ProductName(resultSet.getString("name")),
                    new ProductImage(resultSet.getString("image_url")),
                    new ProductPrice(resultSet.getInt("price"))
            );

    @Override
    public long save(Product product) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE product SET name = ?, image_url = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getImageUrl(), product.getPrice(), product.getId());
        return product;
    }

    @Override
    public Optional<Product> findById(long id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.query(sql, productEntityRowMapper, id).stream()
                .findAny();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productEntityRowMapper);
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
