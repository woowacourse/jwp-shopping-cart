package cart.dao;

import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Product product) {
        String sql = "insert into PRODUCT(name, image, price) values (:name,:image,:price)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("image", product.getImage())
                .addValue("price", product.getPrice());

        jdbcTemplate.update(sql, paramSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Product> findAllProducts() {
        String sql = "select * from PRODUCT";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Product(
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("price")
            );
        });
    }

    public Product findProductById(long id) {
        String sql = "select * from PRODUCT where product_id = :product_id";

        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("product_id", id);
        return jdbcTemplate.queryForObject(sql, paramSource, (rs, rowNum) -> {
            return new Product(
                    rs.getLong("product_id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("price")
            );
        });
    }

    public void updateProduct(Product after) {
        String sql = "update PRODUCT set name = :name, image = :image, price = :price where product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("product_id", after.getProductId())
                .addValue("name", after.getName())
                .addValue("image", after.getImage())
                .addValue("price", after.getPrice());

        jdbcTemplate.update(sql, paramSource);
    }

}
