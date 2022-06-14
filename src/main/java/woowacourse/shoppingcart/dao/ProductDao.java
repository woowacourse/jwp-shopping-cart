package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.entity.ProductEntity;

@Repository
public class ProductDao {

    private static final RowMapper<ProductEntity> ROW_MAPPER = (resultSet, rowNumber) ->
            new ProductEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final ProductEntity productEntity) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setString(3, productEntity.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<ProductEntity> findProductById(final Long productId) {
        final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
        return Optional.ofNullable(DataAccessUtils
                .singleResult(jdbcTemplate.query(query, ROW_MAPPER, productId)));
    }

    public List<ProductEntity> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }

    public List<ProductEntity> findByCartByCustomerId(Long customerId) {
        String query = "SELECT p.id, p.name, p.price, p.image_url "
                + "FROM product p "
                + "JOIN cart_item c "
                + "ON p.id = c.product_id "
                + "WHERE c.customer_id = ?";
        return jdbcTemplate.query(query, ROW_MAPPER, customerId);
    }
}
