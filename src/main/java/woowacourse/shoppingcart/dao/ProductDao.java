package woowacourse.shoppingcart.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNum) ->
            new Product(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("image_url"));

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ProductDao(final DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return insertActor.executeAndReturnKey(parameterSource).longValue();
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT id, name, price, image_url FROM product WHERE id = :productId";
            MapSqlParameterSource parameter = new MapSqlParameterSource("productId", productId);
            return namedParameterJdbcTemplate.queryForObject(query, parameter, PRODUCT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return namedParameterJdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :productId";
        MapSqlParameterSource parameter = new MapSqlParameterSource("productId", productId);
        namedParameterJdbcTemplate.update(query, parameter);
    }
}
