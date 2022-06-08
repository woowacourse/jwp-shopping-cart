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
    private final SimpleJdbcInsert simpleJdbcInsertInProduct;
    private final SimpleJdbcInsert simpleJdbcInsertInImage;

    public ProductDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.simpleJdbcInsertInProduct = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        this.simpleJdbcInsertInImage = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("image")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", product.getName());
        parameterSource.addValue("price", product.getPrice());
        parameterSource.addValue("stock_quantity", product.getStockQuantity());
        final Long productId = simpleJdbcInsertInProduct.executeAndReturnKey(parameterSource).longValue();
        saveImage(productId, product.getImage());

        return productId;
    }

    private Long saveImage(final Long productId, final Image image) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("product_id", productId);
        parameterSource.addValue("image_url", image.getUrl());
        parameterSource.addValue("image_alt", image.getAlt());

        return simpleJdbcInsertInImage.executeAndReturnKey(parameterSource).longValue();
    }

    public Product findProductById(final Long productId) {
        final String query = "SELECT product.id, product.name, product.price, product.stock_quantity,"
                + " image_url, image_alt"
                + " FROM product INNER JOIN image ON product.id = image.product_id"
                + " WHERE product.id = :productId";

        return jdbcTemplate.queryForObject(query, Map.of("productId", productId), PRODUCT_ROW_MAPPER);
    }

    public List<Product> findProducts() {
        final String query = "SELECT product.id, product.name, product.price, product.stock_quantity,"
                + " image_url, image_alt"
                + " FROM product INNER JOIN image ON product.id = image.product_id";

        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void deleteById(final Long productId) {
        final String query = "DELETE FROM product WHERE id = :productId";
        jdbcTemplate.update(query, Map.of("productId", productId));
    }

    public void reduceQuantity(final Long productId, final int quantity) {
        final String sql = "UPDATE product SET stock_quantity = :quantity WHERE id = :productId";
        jdbcTemplate.update(sql, Map.of("quantity", quantity, "productId", productId));
    }

    private RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getInt("stock_quantity"),
            new Image(resultSet.getString("image_url"),
                    resultSet.getString("image_alt"))
    );

}
