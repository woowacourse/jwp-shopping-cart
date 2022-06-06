package woowacourse.shoppingcart.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("image_Url", product.getImageUrl());
        return simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
    }

    public List<Product> findAll() {
        String query = "SELECT id, name, price, image_Url FROM product";
        return template.query(query, PRODUCT_ROW_MAPPER);
    }

//    public Product findProductById(final Long productId) {
//        try {
//            final String query = "SELECT name, price, image_url FROM product WHERE id = ?";
//            return jdbcTemplate.queryForObject(query, (resultSet, rowNumber) ->
//                    new Product(
//                            productId,
//                            resultSet.getString("name"), resultSet.getInt("price"),
//                            resultSet.getString("image_url")
//                    ), productId
//            );
//        } catch (EmptyResultDataAccessException e) {
//            throw new InvalidProductException();
//        }
//    }
//
//    public List<Product> findProducts() {
//        final String query = "SELECT id, name, price, image_url FROM product";
//        return jdbcTemplate.query(query,
//                (resultSet, rowNumber) ->
//                        new Product(
//                                resultSet.getLong("id"),
//                                resultSet.getString("name"),
//                                resultSet.getInt("price"),
//                                resultSet.getString("image_url")
//                        ));
//    }
//
//    public void delete(final Long productId) {
//        final String query = "DELETE FROM product WHERE id = ?";
//        jdbcTemplate.update(query, productId);
//    }
}
