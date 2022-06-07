package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.domain.product.ThumbnailImage;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final RowMapper<Product> productRowMapper = (resultSet, rowMapper) -> new Product(
        resultSet.getLong("id"),
        resultSet.getString("name"),
        resultSet.getInt("price"),
        new ThumbnailImage(resultSet.getString("thumbnail_url"), resultSet.getString("thumbnail_alt"))
    );

    private final RowMapper<ProductStock> productStockRowMapper = (resultSet, rowMapper) -> new ProductStock(
        new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            new ThumbnailImage(resultSet.getString("thumbnail_url"), resultSet.getString("thumbnail_alt"))
        ),
        new Quantity(resultSet.getInt("stock_quantity"))
    );

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductStock save(final Product product, final int stockQuantity) {
        final String query = "INSERT INTO product (name, price, stock_quantity, thumbnail_url, thumbnail_alt) VALUES (?, ?, ?, ?, ?)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement preparedStatement =
                connection.prepareStatement(query, new String[] {"id"});
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setInt(3, stockQuantity);
            preparedStatement.setString(4, product.getThumbnailImageUrl());
            preparedStatement.setString(5, product.getThumbnailImageAlt());
            return preparedStatement;
        }, keyHolder);
        return findProductStockById(keyHolder.getKey().longValue());
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, thumbnail_url, thumbnail_alt FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, productRowMapper, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, thumbnail_url, thumbnail_alt FROM product";
        return jdbcTemplate.query(query, productRowMapper);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }

    public List<ProductStock> findProductStocks() {
        final String query = "SELECT id, name, price, stock_quantity, thumbnail_url, thumbnail_alt FROM product";
        return jdbcTemplate.query(query, productStockRowMapper);
    }

    public ProductStock findProductStockById(Long productId) {
        try {
            final String query = "SELECT id, name, price, stock_quantity, thumbnail_url, thumbnail_alt FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, productStockRowMapper, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }
}
