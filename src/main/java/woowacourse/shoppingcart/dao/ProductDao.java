package woowacourse.shoppingcart.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;

@Repository
public class ProductDao {

    private static final RowMapper<Product> PRODUCT_MAPPER = (rs, rowNum) ->
            new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getString("image_url")
            );

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public List<Product> findProducts(int offset, int limit) {
        String sql = "SELECT id, name, price, stock, image_url FROM PRODUCT LIMIT :limit OFFSET :offset";
        SqlParameterSource params = new MapSqlParameterSource("offset", offset)
                .addValue("limit", limit);
        return jdbcTemplate.query(sql, params, PRODUCT_MAPPER);
    }

    public Product findById(long id) {
        String sql = "SELECT id, name, price, stock, image_url FROM PRODUCT WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, PRODUCT_MAPPER);
    }

    public boolean checkIdExistence(long id) {
        String sql = "SELECT EXISTS (SELECT id FROM PRODUCT WHERE id = :id)";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }

    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM PRODUCT";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
    }
}
