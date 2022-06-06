package woowacourse.shoppingcart.dao;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

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
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url")
            );

    public ProductDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(final Product product) {
        final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("product")
                .usingGeneratedKeyColumns("id");

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl());

        final Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return number.longValue();
    }

    public Optional<Product> findProductById(final long productId) {
        final String sql = "SELECT id, name, price, image_url FROM product WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);

        final List<Product> query = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(parameters), productRowMapper);

        return Optional.ofNullable(DataAccessUtils.singleResult(query));
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(query, productRowMapper);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :id";

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productId);

        namedParameterJdbcTemplate.update(query, new MapSqlParameterSource(parameters));
    }
}
