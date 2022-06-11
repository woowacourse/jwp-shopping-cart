package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.cart.Product;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Product product) {
        final String query = "INSERT INTO product (name, price, image_url, quantity) VALUES (?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                connection.prepareStatement(query, new String[] {"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setInt(4, product.getQuantity());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findProductById(long productId) {
        try {
            final String query = "SELECT name, price, image_url, quantity FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                new Product(
                    productId,
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("quantity")
                ), productId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url, quantity FROM product";
        return jdbcTemplate.query(query,
            (resultSet, rowNumber) ->
                new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("quantity")
                ));
    }

    public int delete(long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(query, productId);
    }
}
