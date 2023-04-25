package cart.persistence.dao;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.entity.Product;
import cart.persistence.entity.ProductCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Component
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product as p";
        return jdbcTemplate.query(query, (result, count) ->
                new Product(result.getLong("id"), result.getString("name"),
                        result.getString("image_url"), result.getInt("price"),
                        ProductCategory.from(result.getString("category"))));
    }

    public Long insert(final Product product) {
        final String query = "INSERT INTO product(name, image_url, price, category) VALUES(?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, product.getName());
            ps.setString(2, product.getImageUrl());
            ps.setInt(3, product.getPrice());
            ps.setString(4, product.getCategory().name());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findById(final Long id) {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product as p " +
                "WHERE p.id = ?";
        try {
            return jdbcTemplate.queryForObject(query, (result, count) ->
                    new Product(result.getLong("id"), result.getString("name"),
                            result.getString("image_url"), result.getInt("price"),
                            ProductCategory.from(result.getString("category"))), id);
        } catch (EmptyResultDataAccessException exception) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void update(final Product product) {
        final String query = "UPDATE product AS p SET p.name = ?, p.image_url = ?, p.price = ?, p.category = ? " +
                "WHERE p.id = ?";
        jdbcTemplate.update(query, product.getName(), product.getImageUrl(), product.getPrice(),
                product.getCategory().name(), product.getId());
    }

    public void deleteById(final Long id) {
        final String query = "DELETE FROM product p WHERE p.id = ?";
        jdbcTemplate.update(query, id);
    }
}
