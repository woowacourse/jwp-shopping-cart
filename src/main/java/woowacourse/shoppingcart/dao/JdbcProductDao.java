package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@Repository
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url, description, stock) VALUES (?, ?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice().getValue());
            preparedStatement.setString(3, product.getImageUrl());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getStock().getValue());
            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public ProductEntity findProductById(final Long productId) {
        final String query = "SELECT name, price, image_url, description, stock FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                new ProductEntity(
                        productId,
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url"),
                        resultSet.getString("description"),
                        resultSet.getInt("stock")
                ), productId
        );
    }

    @Override
    public List<ProductEntity> findProducts() {
        final String query = "SELECT id, name, price, image_url, description, stock FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new ProductEntity(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url"),
                                resultSet.getString("description"),
                                resultSet.getInt("stock")
                        ));
    }

    @Override
    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
