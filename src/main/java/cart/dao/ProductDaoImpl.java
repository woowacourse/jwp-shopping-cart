package cart.dao;

import cart.domain.product.ProductRepository;
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
public class ProductDaoImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final RowMapper<ProductEntity> productRowMapper = (result, count) ->
            new ProductEntity(result.getLong("id"),
                    result.getString("name"),
                    result.getString("image_url"),
                    result.getInt("price"),
                    ProductCategory.from(result.getString("category"))
            );

    @Override
    public Long insert(final ProductEntity productEntity) {
        final String query = "INSERT INTO product(name, image_url, price, category) VALUES(?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, productEntity.getName());
            ps.setString(2, productEntity.getImageUrl());
            ps.setInt(3, productEntity.getPrice());
            ps.setString(4, productEntity.getCategory().name());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Optional<ProductEntity> findById(final Long id) {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p WHERE p.id = ?";
        try {
            final ProductEntity productEntity = jdbcTemplate.queryForObject(query, productRowMapper, id);
            return Optional.of(productEntity);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public List<ProductEntity> findAll() {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product p";
        return jdbcTemplate.query(query, productRowMapper);
    }

    @Override
    public int update(final Long productId, final ProductEntity productEntity) {
        final String query = "UPDATE product p SET p.name = ?, p.image_url = ?, p.price = ?, p.category = ? WHERE p.id = ?";
        return jdbcTemplate.update(query, productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice(),
                productEntity.getCategory().name(), productId);
    }

    @Override
    public int deleteById(final Long id) {
        final String query = "DELETE FROM product p WHERE p.id = ?";
        return jdbcTemplate.update(query, id);
    }
}
