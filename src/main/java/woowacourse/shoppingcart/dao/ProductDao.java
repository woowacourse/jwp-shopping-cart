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

    public List<Product> findAll() {
        String sql = "SELECT id, name, price, stock, image_url FROM product";
        return jdbcTemplate.query(sql, PRODUCT_MAPPER);
    }

    public int findStockById(long id) {
        String sql = "SELECT stock FROM PRODUCT WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public boolean existsId(long id) {
        String sql = "SELECT EXISTS (SELECT id FROM PRODUCT WHERE id = :id)";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, params, Boolean.class);
    }
}
