package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, thumbnail_url, quantity) VALUES (?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                    connection.prepareStatement(query, new String[]{"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getThumbnailUrl());
            preparedStatement.setInt(4, product.getQuantity());

            return preparedStatement;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<Product> findProductById(final Long productId) {
        final String query = "SELECT id, name, price, thumbnail_url, quantity FROM product WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Product(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("thumbnail_url"),
                            resultSet.getInt("quantity")), productId
            ));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, thumbnail_url, quantity FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("thumbnail_url"),
                                resultSet.getInt("quantity")));
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        findProductById(productId).orElseThrow(NotFoundProductException::new);
        jdbcTemplate.update(query, productId);
    }

    public int saveAll(List<Product> products) {
        final String query = "INSERT INTO product(name, price, thumbnail_url, quantity) VALUES(?,?,?,?)";
        return jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, products.get(i).getName());
                ps.setInt(2, products.get(i).getPrice());
                ps.setString(3, products.get(i).getThumbnailUrl());
                ps.setInt(4, products.get(i).getQuantity());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        }).length;
    }
}
