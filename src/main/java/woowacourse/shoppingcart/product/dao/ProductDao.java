package woowacourse.shoppingcart.product.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ThumbnailImage;
import woowacourse.shoppingcart.exception.NotExistException;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Product save(final Product product) {
        final ThumbnailImage thumbnailImage = product.getThumbnailImage();
        final SimpleJdbcInsert productSimpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        final SimpleJdbcInsert imagesSimpleInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("images")
                .usingGeneratedKeyColumns("id");

        final SqlParameterSource productParams = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("stock_quantity", product.getStockQuantity());

        final Long id = productSimpleInsert.executeAndReturnKey(productParams).longValue();

        final SqlParameterSource imagePrams = new MapSqlParameterSource()
                .addValue("product_id", id)
                .addValue("url", thumbnailImage.getUrl())
                .addValue("alt", thumbnailImage.getAlt());

        imagesSimpleInsert.execute(imagePrams);

        return new Product(id, product.getName(), product.getPrice(), product.getStockQuantity(),
                new ThumbnailImage(thumbnailImage.getUrl(), thumbnailImage.getAlt()));
    }

    public Product findProductById(final Long productId) {
        try {
            final String sql =
                    "select p.id as id, p.name as name, p.price as price, p.stock_quantity as quantity, i.url as url, i.alt as alt "
                            + "from product as p "
                            + "inner join images as i on p.id = i.product_id "
                            + "where p.id = ?";
            return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) ->
                    new Product(
                            productId,
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getLong("quantity"),
                            new ThumbnailImage(resultSet.getString("url"), resultSet.getString("alt"))
                    ), productId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotExistException("존재하지 않는 상품입니다.", ErrorResponse.NOT_EXIST_PRODUCT);
        }
    }

    public List<Product> findProducts() {
        final String sql =
                "select p.id as id, p.name as name, p.price as price, p.stock_quantity as quantity, i.url as url, i.alt as alt "
                        + "from product as p "
                        + "inner join images as i on p.id = i.product_id";
        return jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("price"),
                                rs.getLong("quantity"),
                                new ThumbnailImage(rs.getString("url"), rs.getString("alt"))
                        )
        );
    }

    public void updateStockQuantityById(Long id, Integer quantity) {
        final String sql = "update product set stock_quantity = ? where id = ?";
        jdbcTemplate.update(sql, quantity, id);
    }
}
