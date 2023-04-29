package cart.persistence.dao;

import cart.persistence.entity.ProductEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product as p";
        return jdbcTemplate.query(query, productRowMapper());
    }

    public Long insert(final ProductEntity productEntity) {
        final String query = "INSERT INTO product(name, image_url, price, category) VALUES(?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setString(1, productEntity.getName());
            ps.setString(2, productEntity.getImageUrl());
            ps.setInt(3, productEntity.getPrice());
            ps.setString(4, productEntity.getCategory());
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public int updateById(final ProductEntity productEntity, final Long id) {
        final String query = "UPDATE product AS p SET p.name = ?, p.image_url = ?, p.price = ?, p.category = ? " +
                "WHERE p.id = ?";
        return jdbcTemplate.update(query, productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice(),
                productEntity.getCategory(), id);
    }

    public int deleteById(final Long id) {
        final String query = "DELETE FROM product p WHERE p.id = ?";
        return jdbcTemplate.update(query, id);
    }

    public Optional<ProductEntity> findById(final Long id) {
        final String query = "SELECT p.id, p.name, p.image_url, p.price, p.category FROM product as p " +
                "WHERE p.id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, productRowMapper(), id));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    private RowMapper<ProductEntity> productRowMapper() {
        return (result, count) ->
                new ProductEntity(result.getLong("id"), result.getString("name"),
                        result.getString("image_url"), result.getInt("price"),
                        result.getString("category"));
    }
}
