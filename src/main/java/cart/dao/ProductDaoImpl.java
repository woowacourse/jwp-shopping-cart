package cart.dao;

import cart.dto.ProductRequestDto;
import cart.entity.Product;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;


    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insertProduct(Product product) {
        String sql = "INSERT INTO product(name, image_url, price) VALUES(?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getName());
                ps.setString(2, product.getImageUrl());
                ps.setInt(3, product.getPrice());
                return ps;
            }, keyHolder);

        return Long.valueOf(keyHolder.getKeys().get("id").toString());
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(final Long id) {
        return null;
    }

    @Override
    public Product updateProduct(final Long id, final ProductRequestDto productRequestDto) {
        return null;
    }

    @Override
    public Long deleteProduct(final Long id) {
        return null;
    }
}
