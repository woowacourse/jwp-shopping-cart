package woowacourse.shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;
import woowacourse.shoppingcart.exception.notfound.ProductNotFoundException;

@Repository
public class JdbcProductDao implements ProductDao {
    private static final String TABLE_NAME = "product";
    private static final String KEY_COLUMN = "id";

    private static final RowMapper<ProductEntity> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new ProductEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("image_url")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcProductDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN);
    }

    public Long save(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("description", product.getDescription());
        params.put("price", product.getPrice());
        params.put("stock", product.getStock());
        params.put("image_url", product.getImageUrl());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public ProductEntity findById(long productId) {
        String query = "SELECT id, name, description, price, stock, image_url FROM product WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(query, PRODUCT_ROW_MAPPER, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException();
        }
    }

    public List<ProductEntity> findAll() {
        final String query = "SELECT id, name, description, price, stock, image_url FROM product";
        return jdbcTemplate.query(query, PRODUCT_ROW_MAPPER);
    }

    public void delete(long productId) {
        final String query = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(query, productId);
    }
}
