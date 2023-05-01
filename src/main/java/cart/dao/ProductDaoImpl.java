package cart.dao;

import cart.domain.product.Product;
import cart.domain.product.ProductDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductDaoImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<Product> productRowMapper = (result, count) ->
            new Product(result.getLong("id"),
                    result.getString("name"),
                    result.getString("image_url"),
                    result.getInt("price"),
                    ProductCategory.from(result.getString("category"))
            );

    @Override
    public Long insert(final Product product) {
        final String query = "INSERT INTO product(name, image_url, price, category) VALUES(?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, product.getProductNameValue());
            ps.setString(2, product.getImageUrlValue());
            ps.setInt(3, product.getPriceValue());
            ps.setString(4, product.getCategory().name());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<Product> findById(final Long id) {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p WHERE p.id = ?";
        try {
            final Product product = jdbcTemplate.queryForObject(query, productRowMapper, id);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p";
        return jdbcTemplate.query(query, productRowMapper);
    }

    @Override
    public int update(final Long productId, final Product product) {
        final String query = "UPDATE product p SET p.name = ?, p.image_url = ?, p.price = ?, p.category = ? WHERE p.id = ?";
        return jdbcTemplate.update(query, product.getProductNameValue(), product.getImageUrlValue(), product.getPriceValue(),
                product.getCategory().name(), productId);
    }

    @Override
    public int deleteById(final Long id) {
        final String query = "DELETE FROM product p WHERE p.id = ?";
        return jdbcTemplate.update(query, id);
    }
}
