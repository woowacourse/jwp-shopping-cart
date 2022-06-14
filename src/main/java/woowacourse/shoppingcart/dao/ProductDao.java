package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private static final int OFFSET = 1;

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public Product findProductById(final Long productId) {
        final String query = "SELECT id, name, price, image_url FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(query, getRowMapper(), productId);
    }

    private RowMapper<Product> getRowMapper() {
        return (resultSet, rowNumber) ->
                new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url")
                );
    }

    public Integer findProductsCount() {
        final String query = "SELECT COUNT(id) FROM product";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public int delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(query, productId);
    }

    public List<Product> findProductsByPage(final int limit, final int page) {
        final String sql = "SELECT id, name, price, image_url FROM product limit ? OFFSET ?";
        return jdbcTemplate.query(sql, getRowMapper(), limit, (page - OFFSET) * limit);
    }
}
