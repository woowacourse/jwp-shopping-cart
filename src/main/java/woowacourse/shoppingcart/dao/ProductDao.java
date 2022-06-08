package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Locale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

import javax.sql.DataSource;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Product save(Product product) {
        String name = product.getName();
        int price = product.getPrice();
        String imageUrl = product.getImageUrl();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name.toLowerCase(Locale.ROOT))
                .addValue("price", price)
                .addValue("image_url", imageUrl);

        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Product(id, name, price, imageUrl);
    }

    public List<Product> findProducts(Long start, Long end) {
        final String query = "SELECT id, name, price, image_url FROM product LIMIT ?, ?";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url")), start, end);
    }

    public List<Product> findAllProducts() {
        final String query = "SELECT id, name, price, image_url FROM product";
        return jdbcTemplate.query(query,
                (resultSet, rowNumber) ->
                        new Product(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getInt("price"),
                                resultSet.getString("image_url")));
    }

    public Product findProductById(final Long productId) {
        try {
            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
                    new Product(
                            productId,
                            resultSet.getString("name"), resultSet.getInt("price"),
                            resultSet.getString("image_url")
                    ), productId);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
