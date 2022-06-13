package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :imageurl)";
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("name", product.getName());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("imageurl", product.getImageUrl());
        namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = :productid";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource("productid", productId);

            return namedParameterJdbcTemplate.queryForObject(query, parameterSource, rowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(query, rowMapper());
    }

    private RowMapper<Product> rowMapper() {
        return (resultSet, rowNumber) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
        );
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", productId);
        namedParameterJdbcTemplate.update(query, parameterSource);
    }
}
