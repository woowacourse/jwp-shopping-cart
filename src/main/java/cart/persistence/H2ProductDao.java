package cart.persistence;

import cart.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Product> findAll() {
        String sql = "SELECT id, name, image_url, price FROM PRODUCT";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("image_url"),
                rs.getBigDecimal("price")
        ));
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE PRODUCT SET id=?, name=?, image_url=?, price=? WHERE id=?";
        jdbcTemplate.update(sql,
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice(),
                product.getId()
        );
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM PRODUCT WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
