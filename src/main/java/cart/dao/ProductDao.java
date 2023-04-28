package cart.dao;

import cart.domain.Product;
import cart.dto.request.ProductSaveRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("product_id"),
            rs.getString("name"),
            rs.getString("image"),
            rs.getLong("price")
    );

    public ProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(ProductSaveRequest saveRequest) {
        String sql = "insert into PRODUCT(name, image, price) values (:name,:image,:price)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", saveRequest.getName())
                .addValue("image", saveRequest.getImage())
                .addValue("price", saveRequest.getPrice());

        jdbcTemplate.update(sql, paramSource, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Product> findAllProducts() {
        String sql = "select * from PRODUCT";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findProductById(long id) {
        String sql = "select * from PRODUCT where product_id = :product_id";

        SqlParameterSource paramSource = new MapSqlParameterSource().addValue("product_id", id);
        return jdbcTemplate.queryForObject(sql, paramSource, productRowMapper);
    }

    public void updateProduct(Product product) {
        String sql = "update PRODUCT set name = :name, image = :image, price = :price where product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("product_id", product.getProductId())
                .addValue("name", product.getName().getName())
                .addValue("image", product.getImage())
                .addValue("price", product.getPrice().getPrice());

        jdbcTemplate.update(sql, paramSource);
    }

    public void deleteProduct(long productId) {
        String sql = "delete from PRODUCT where product_id = :product_id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("product_id", productId);

        jdbcTemplate.update(sql, paramSource);
    }
}
