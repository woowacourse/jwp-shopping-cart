package cart.dao;

import cart.domain.Product;
import cart.domain.ProductCategory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> findAll() {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product as p";
        return jdbcTemplate.query(query, (result, count) ->
            new Product(result.getLong("id"), result.getString("name"),
                    result.getString("image_url"), result.getInt("price"),
                    result.getObject("category", ProductCategory.class)));
    }
}
