package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        Long imageId = saveImage(product.getImage());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("stock_quantity", product.getStockQuantity());
        parameterSource.addValue("image_id", imageId);

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    private Long saveImage(Image image) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("image_url", image.getUrl());
        parameterSource.addValue("image_alt", image.getAlt());

        return simpleJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public Product findProductById(final Long id) {
        final String query = "SELECT id, name, price, stock_quantity,"
                + " image.url AS image_url, image.alt AS image_alt"
                + " FROM product INNER JOIN image ON product.image_id = image.id"
                + " WHERE id = :id";

        return jdbcTemplate.queryForObject(query, Map.of("id", id), PRODUCT_ROW_MAPPER);
    }

    public List<Product> findProducts() {
        final String query = "SELECT id, name, price, stock_quantity,"
                + " image.url AS image_url, image.alt AS image_alt"
                + " FROM product INNER JOIN image ON product.image_id = image.id";

        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void deleteById(final Long id) {
        final String query = "DELETE FROM product WHERE id = :id";
        jdbcTemplate.update(query, Map.of("id", id));
    }

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getInt("stock_quantity"),
            new Image(resultSet.getString("image_url"),
                    resultSet.getString("image_alt"))
    );
}
