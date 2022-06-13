package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String IMAGE_URL = "image_url";
    private static final String ID = "id";

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{ID});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Product(
                            productId,
                            resultSet.getString(NAME),
                            resultSet.getInt(PRICE),
                            resultSet.getString(IMAGE_URL)
                    ), productId
            );
        } catch (final EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong(ID),
                                resultSet.getString(NAME),
                                resultSet.getInt(PRICE),
                                resultSet.getString(IMAGE_URL)
                        ));
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
