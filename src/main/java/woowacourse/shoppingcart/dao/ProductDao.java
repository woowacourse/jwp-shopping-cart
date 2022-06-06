package woowacourse.shoppingcart.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.DataNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static Product rowMapper(final ResultSet rs, int row) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url")
        );
    }

    public Long save(final Product product) {
        final String sql = "INSERT INTO product (name, price, image_url) VALUES (:name, :price, :image_url)";
        final Map<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("image_url", product.getImageUrl());

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String sql = "SELECT id, name, price, image_url FROM product WHERE id = :id";
            final Map<String, Object> params = new HashMap<>();
            params.put("id", productId);

            return namedParameterJdbcTemplate.queryForObject(sql, params, ProductDao::rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException();
        }
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(sql, ProductDao::rowMapper);
    }

    public void delete(final Long productId) {
        final String sql = "DELETE FROM product WHERE id = :id";
        final Map<String, Object> params = new HashMap<>();
        params.put("id", productId);

        namedParameterJdbcTemplate.update(sql, params);
    }
}
