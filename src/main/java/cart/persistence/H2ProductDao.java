package cart.persistence;

import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H2ProductDao implements ProductDao {

    private JdbcTemplate jdbcTemplate;

    public H2ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Product product) {
        String sql = "INSERT INTO PRODUCT(name, image_url, price) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getImage(), product.getPrice());
    }
}
