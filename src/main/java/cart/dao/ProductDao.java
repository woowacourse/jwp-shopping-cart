package cart.dao;

import cart.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Product product) {
        String sql = "insert into PRODUCT(name, image, price) values (:name,:image,:price)";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("image", product.getImage())
                .addValue("price", product.getPrice());

        return jdbcTemplate.update(sql, paramSource);
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
}
