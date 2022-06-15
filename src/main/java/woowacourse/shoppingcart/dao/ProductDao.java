package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("image_url")
            );

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Product product) {
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        return simpleJdbcInsert.executeAndReturnKey(parameters)
                .longValue();
    }

    public Optional<Product> findProductById(Long productId) {
        final String sql = "SELECT id, name, price, stock, image_url FROM product WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", productId);
        return namedParameterJdbcTemplate.query(sql, parameters, PRODUCT_MAPPER)
                .stream()
                .findAny();
    }

    public List<Product> findProducts() {
        final String sql = "SELECT id, name, price, stock, image_url FROM product";
        return namedParameterJdbcTemplate.query(sql, PRODUCT_MAPPER);
    }

    public List<Product> findProductsByPaging(int page, int limit) {
        int passedId = (page - 1) * limit;
        String sql = "SELECT id, name, price, stock, image_url FROM product "
                + "WHERE id > :passed_id ORDER BY id LIMIT 0, :limit";
        MapSqlParameterSource parameters = new MapSqlParameterSource("passed_id", passedId)
                .addValue("limit", limit);
        return namedParameterJdbcTemplate.query(sql, parameters, PRODUCT_MAPPER);
    }

    public boolean exists(Long id) {
        String sql = "SELECT EXISTS (SELECT 1 FROM product WHERE id = :id)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Boolean.class);
    }

    public void updateStock(Product product) {
        String sql = "UPDATE product SET stock = :stock WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", product.getId())
                .addValue("stock", product.getStock());
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public void delete(Long productId) {
        final String sql = "DELETE FROM product WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", productId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    public int countAll() {
        String sql = "SELECT COUNT(id) FROM product";
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }
}
