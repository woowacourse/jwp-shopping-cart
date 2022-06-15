package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.domain.product.Product;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RowMapper<Product> productRowMapper = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    new Name(resultSet.getString("name")),
                    new Price(resultSet.getInt("price")),
                    new ImageUrl(resultSet.getString("image_url"))
            );

    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product save(final Product product) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl());

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Product(
                number.longValue(),
                new Name(product.getName()),
                new Price(product.getPrice()),
                new ImageUrl(product.getImageUrl()));
    }

    public Optional<Product> findProductById(final Long productId) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = :id";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);

        final List<Product> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters),
                productRowMapper);
        return Optional.ofNullable(DataAccessUtils.singleResult(query));
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(sql, productRowMapper);
    }

    public int delete(final Long productId) {
        final String sql = "DELETE FROM product WHERE id = :id";
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);
        return namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(parameters));
    }
}
