package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Repository
public class ProductDao {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    public ProductDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(final Product product) {
        SqlParameterSource sqlParameter = new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("image_url", product.getImageUrl());

        return simpleJdbcInsert.executeAndReturnKey(sqlParameter).longValue();
    }

    public Product findById(final Long id) {
        try {
            String query = "SELECT id, name, price, image_url FROM product WHERE id = :id";
            SqlParameterSource nameParameters = new MapSqlParameterSource("id", id);

            return template.queryForObject(query, nameParameters, PRODUCT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidProductException();
        }
    }

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

    public void delete(final Long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
//        jdbcTemplate.update(query, productId);
    }

}
