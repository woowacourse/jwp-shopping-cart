package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.domain.InvalidProductException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url, is_deleted, description) "
            + "VALUES (?, ?, ?, 0, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                connection.prepareStatement(query, new String[] {"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setString(4, product.getDescription());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Product> findProductById(final Long productId) {
        try {
            final String query = "SELECT * FROM product WHERE id = ? and is_deleted = 0";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                new Product(
                    productId,
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getString("description")
                ), productId
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT * FROM product WHERE is_deleted = 0";
        return jdbcTemplate.query(query,
            (resultSet, rowNumber) ->
                new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getString("description")
                ));
    }

    public void delete(final Long productId) {
        final String query = "UPDATE product SET is_deleted = 1 WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
