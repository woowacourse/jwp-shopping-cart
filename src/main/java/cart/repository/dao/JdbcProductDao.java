package cart.repository.dao;

import cart.repository.entity.ProductEntity;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ProductEntity> actorRowMapper = (resultSet, rowNum) -> new ProductEntity(
            resultSet.getLong("product_id"),
            resultSet.getString("name"),
            resultSet.getString("image_url"),
            resultSet.getInt("price")
    );

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final ProductEntity productEntity) {
        final String sql = "INSERT INTO product (name, image_url, price) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setString(2, productEntity.getImageUrl());
            preparedStatement.setInt(3, productEntity.getPrice());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<ProductEntity> findAll() {
        final String sql = "SELECT product_id, name, image_url, price FROM product";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int update(final ProductEntity productEntity) {
        final String sql = "UPDATE product SET name = ?, image_url = ?, price = ?";
        return jdbcTemplate.update(sql, productEntity.getName(), productEntity.getImageUrl(), productEntity.getPrice());
    }

    @Override
    public int delete(final Long id) {
        final String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
