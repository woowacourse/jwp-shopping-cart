package woowacourse.shoppingcart.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ProductDao(final NamedParameterJdbcTemplate namedJdbcTemplate,
                      final DataSource dataSource) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        final String query = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        final Map<String, Object> params = Map.ofEntries(
                Map.entry("name", product.getName()),
                Map.entry("price", product.getPrice()),
                Map.entry("image_url", product.getImageUrl())
        );
        return simpleInsert.executeAndReturnKey(params).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = :productId";
            final SqlParameterSource params = new MapSqlParameterSource(Map.of("productId", productId));
            return namedJdbcTemplate.queryForObject(query, params, (resultSet, rowNumber) -> {
                return new Product(productId, resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url"));
            });
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return namedJdbcTemplate.query(query, (resultSet, rowNum) -> {
            return new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );
        });
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :productId";
        namedJdbcTemplate.update(query, Map.of("productId", productId));
    }
}
