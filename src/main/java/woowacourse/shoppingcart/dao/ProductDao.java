package woowacourse.shoppingcart.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Product> productRowMapper = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    new ProductName(resultSet.getString("name")),
                    resultSet.getInt("price"),
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
        parameters.put("name", product.getName().getValue());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl().getValue());

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Product(number.longValue(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Optional<Product> findById(final long productId) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);

        final List<Product> queryResult = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), productRowMapper);

        return Optional.ofNullable(DataAccessUtils.singleResult(queryResult));
    }

    public List<Product> findAll() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(query, productRowMapper);
    }

    public int delete(final long productId) {
        final String query = "DELETE FROM product WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);

        return namedParameterJdbcTemplate.update(query, new MapSqlParameterSource(parameters));
    }
}
