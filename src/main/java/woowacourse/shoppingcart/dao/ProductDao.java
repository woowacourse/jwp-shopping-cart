package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Product rowMapperToProduct(final Long productId, final ResultSet resultSet) throws SQLException {
        return new Product(
                productId,
                resultSet.getString("name"), resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImageUrl());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Product> findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
            Product product = jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    rowMapperToProduct(productId, resultSet), productId
            );
            return Optional.ofNullable(product);
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public int countProducts() {
        final String query = "SELECT COUNT(*) FROM product";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public List<Product> findProducts(final PagingIndex pagingIndex) {
        final String query = "SELECT id, name, price, image_url FROM product LIMIT ?, ?";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) -> rowMapperToProduct(resultSet.getLong("id"), resultSet),
                pagingIndex.getStartIndex(),
                pagingIndex.getLimit()
        );
    }

    public int delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(query, productId);
    }
}
