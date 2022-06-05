package woowacourse.product.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import woowacourse.product.domain.Product;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductDao(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        final SqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", product.getName())
            .addValue("price", product.getPrice().getValue())
            .addValue("stock", product.getStock().getValue())
            .addValue("imageURL", product.getImageURL());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    // public Product findProductById(final Long productId) {
    //     try {
    //         final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
    //         return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
    //                 new Product(
    //                         productId,
    //                         resultSet.getString("name"), resultSet.getInt("price"),
    //                         resultSet.getString("image_url")
    //                 ), productId
    //         );
    //     } catch (EmptyResultDataAccessException e) {
    //         throw new InvalidProductException();
    //     }
    // }
    //
    // public List<Product> findProducts() {
    //     final String query = "SELECT id, name, price, image_url FROM product";
    //     return jdbcTemplate.query(query,
    //             (resultSet, rowNumber) ->
    //                     new Product(
    //                             resultSet.getLong("id"),
    //                             resultSet.getString("name"),
    //                             resultSet.getInt("price"),
    //                             resultSet.getString("image_url")
    //                     ));
    // }
    //
    // public void delete(final Long productId) {
    //     final String query = "DELETE FROM product WHERE id = ?";
    //     jdbcTemplate.update(query, productId);
    // }
}
